package com.elfeky.devdash.ui.screens.details_screens.company

import androidx.lifecycle.viewModelScope
import com.elfeky.devdash.ui.base.BaseViewModel
import com.elfeky.devdash.ui.common.dialogs.company.model.CompanyUiModel
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.model.tenant.TenantRequest
import com.elfeky.domain.usecase.account.GetUserProfileUseCase
import com.elfeky.domain.usecase.pin.get.GetPinnedProjectsUseCase
import com.elfeky.domain.usecase.pin.get.GetPinnedTenantsUseCase
import com.elfeky.domain.usecase.pin.pin.PinProjectUseCase
import com.elfeky.domain.usecase.pin.pin.PinTenantUseCase
import com.elfeky.domain.usecase.pin.unpin.UnpinProjectUseCase
import com.elfeky.domain.usecase.pin.unpin.UnpinTenantUseCase
import com.elfeky.domain.usecase.project.AddProjectUseCase
import com.elfeky.domain.usecase.project.DeleteProjectUseCase
import com.elfeky.domain.usecase.project.GetTenantProjectsUseCase
import com.elfeky.domain.usecase.tenant.DeleteTenantUseCase
import com.elfeky.domain.usecase.tenant.GetTenantByIdUseCase
import com.elfeky.domain.usecase.tenant.UpdateTenantUseCase
import com.elfeky.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CompanyDetailsViewModel.Factory::class)
class CompanyDetailsViewModel @AssistedInject constructor(
    @Assisted private val companyId: Int,
    private val getTenantByIdUseCase: GetTenantByIdUseCase,
    private val deleteTenantUseCase: DeleteTenantUseCase,
    private val updateTenantUseCase: UpdateTenantUseCase,
    private val getPinnedTenantsUseCase: GetPinnedTenantsUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val addProjectUseCase: AddProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val getTenantProjectsUseCase: GetTenantProjectsUseCase,
    private val getPinnedProjectsUseCase: GetPinnedProjectsUseCase,
    private val pinTenantUseCase: PinTenantUseCase,
    private val unpinTenantUseCase: UnpinTenantUseCase,
    private val pinProjectUseCase: PinProjectUseCase,
    private val unpinProjectUseCase: UnpinProjectUseCase,
) : BaseViewModel<CompanyDetailsReducer.State, CompanyDetailsReducer.Event, CompanyDetailsReducer.Effect>(
    CompanyDetailsReducer.initialState,
    CompanyDetailsReducer()
) {

    @AssistedFactory
    interface Factory {
        fun create(tenantId: Int): CompanyDetailsViewModel
    }

    init {
        observeEffects()
        loadAllCompanyData()
    }

    fun onEvent(event: CompanyDetailsReducer.Event) {
        when (event) {
            is CompanyDetailsReducer.Event.Update,
            is CompanyDetailsReducer.Event.DismissDialogClicked -> sendEvent(event)

            else -> sendEventForEffect(event)
        }
    }

    private fun observeEffects() {
        viewModelScope.launch {
            internalEffect.collect { effect ->
                when (effect) {
                    CompanyDetailsReducer.Effect.TriggerReloadAllData -> loadAllCompanyData()
                    CompanyDetailsReducer.Effect.TriggerDeleteCompany -> deleteCompany()
                    is CompanyDetailsReducer.Effect.TriggerUpdateCompany -> updateCompany(effect.companyUiModel)
                    CompanyDetailsReducer.Effect.TriggerToggleCompanyPin -> toggleTenantPin()
                    is CompanyDetailsReducer.Effect.TriggerAddProject -> addProject(effect.projectRequest)
                    is CompanyDetailsReducer.Effect.TriggerDeleteProject -> deleteProject(effect.projectId)
                    is CompanyDetailsReducer.Effect.TriggerToggleProjectPin -> toggleProjectPin(
                        effect.projectId
                    )

                    is CompanyDetailsReducer.Effect.ShowSnackbar,
                    is CompanyDetailsReducer.Effect.ShowDialog,
                    CompanyDetailsReducer.Effect.NavigateBack,
                    is CompanyDetailsReducer.Effect.NavigateToProjectDetails -> sendUiEffect(effect)
                }
            }
        }
    }

    private fun loadAllCompanyData() {
        viewModelScope.launch {
            sendEvent(CompanyDetailsReducer.Event.Update.IsLoading(true))
            runCatching {
                loadCompany()
                loadPinnedStatus()
                loadUserProfile()
            }
            sendEvent(CompanyDetailsReducer.Event.Update.IsLoading(false))

            sendEvent(CompanyDetailsReducer.Event.Update.ProjectsLoading(true))
            runCatching {
                loadCompanyProjects()
                loadPinnedProjects()
            }
            sendEvent(CompanyDetailsReducer.Event.Update.ProjectsLoading(false))
        }
    }

    private suspend fun <T> collectResource(
        resourceFlow: Flow<Resource<T>>,
        successEvent: (T) -> CompanyDetailsReducer.Event.Update,
        errorEvent: CompanyDetailsReducer.Event.Error,
        loadingEvent: CompanyDetailsReducer.Event.Update? = null
    ) {
        resourceFlow.collect { result ->
            when (result) {
                is Resource.Success -> result.data?.let { sendEvent(successEvent(it)) }
                is Resource.Error -> sendEvent(errorEvent)
                is Resource.Loading -> loadingEvent?.let { sendEvent(it) } ?: Unit
            }
        }
    }

    private suspend fun loadCompany() {
        collectResource(
            resourceFlow = getTenantByIdUseCase(companyId),
            successEvent = { CompanyDetailsReducer.Event.Update.Company(it) },
            errorEvent = CompanyDetailsReducer.Event.Error.CompanyLoadError
        )
    }

    private suspend fun loadPinnedStatus() {
        getPinnedTenantsUseCase().collect { result ->
            when (result) {
                is Resource.Success -> sendEvent(
                    CompanyDetailsReducer.Event.Update.PinnedStatus(
                        result.data?.any { tenant -> tenant.id == companyId } == true
                    )
                )

                is Resource.Error -> sendEvent(CompanyDetailsReducer.Event.Error.PinnedTenantsLoadError)
                else -> Unit
            }
        }
    }

    private suspend fun loadUserProfile() {
        collectResource(
            resourceFlow = getUserProfileUseCase(),
            successEvent = { CompanyDetailsReducer.Event.Update.UserId(it.id) },
            errorEvent = CompanyDetailsReducer.Event.Error.UserProfileLoadError
        )
    }

    private suspend fun loadCompanyProjects() {
        collectResource(
            resourceFlow = getTenantProjectsUseCase(companyId),
            successEvent = { CompanyDetailsReducer.Event.Update.Projects(it) },
            errorEvent = CompanyDetailsReducer.Event.Error.ProjectsLoadError
        )
    }

    private suspend fun loadPinnedProjects() {
        collectResource(
            resourceFlow = getPinnedProjectsUseCase(),
            successEvent = { CompanyDetailsReducer.Event.Update.PinnedProjects(it) },
            errorEvent = CompanyDetailsReducer.Event.Error.PinnedProjectsLoadError
        )
    }

    private fun deleteCompany() {
        viewModelScope.launch {
            deleteTenantUseCase(companyId).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> sendEvent(CompanyDetailsReducer.Event.CompanyAction.DeletionCompleted)
                    is Resource.Error -> sendEvent(CompanyDetailsReducer.Event.Error.CompanyDeleteError)
                }
            }
        }
    }

    private fun updateCompany(companyUiModel: CompanyUiModel) {
        val request = TenantRequest(
            description = companyUiModel.description,
            image = companyUiModel.logoUri?.toString(),
            keywords = companyUiModel.keywords,
            name = companyUiModel.title,
            tenantUrl = companyUiModel.websiteUrl
        )
        viewModelScope.launch {
            updateTenantUseCase(request, companyId).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> sendEvent(CompanyDetailsReducer.Event.CompanyAction.UpdateCompleted)
                    is Resource.Error -> sendEvent(CompanyDetailsReducer.Event.Error.CompanyUpdateError)
                }
            }
        }
    }

    private fun toggleTenantPin() {
        viewModelScope.launch {
            val isCurrentlyPinned = state.value.isPinned
            val operation =
                if (isCurrentlyPinned) unpinTenantUseCase(companyId) else pinTenantUseCase(companyId)
            operation.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        sendEvent(CompanyDetailsReducer.Event.Update.PinnedStatus(!isCurrentlyPinned))
                        sendEvent(CompanyDetailsReducer.Event.CompanyAction.PinCompleted(!isCurrentlyPinned))
                    }

                    is Resource.Error -> sendEvent(CompanyDetailsReducer.Event.Error.CompanyPinError)
                    is Resource.Loading -> Unit
                }
            }
        }
    }

    private fun addProject(projectRequest: ProjectRequest) {
        viewModelScope.launch {
            addProjectUseCase(projectRequest, companyId).collect { result ->
                when (result) {
                    is Resource.Success -> result.data?.let {
                        sendEvent(CompanyDetailsReducer.Event.ProjectAction.Added(it))
                    }

                    is Resource.Error -> sendEvent(CompanyDetailsReducer.Event.Error.ProjectAddError)
                    is Resource.Loading -> Unit
                }
            }
        }
    }

    private fun deleteProject(projectId: Int) {
        viewModelScope.launch {
            deleteProjectUseCase(projectId).collect { result ->
                when (result) {
                    is Resource.Loading -> sendEvent(
                        CompanyDetailsReducer.Event.Update.ProjectsLoading(
                            true
                        )
                    )

                    is Resource.Success -> sendEvent(CompanyDetailsReducer.Event.ProjectAction.DeletionCompleted)
                    is Resource.Error -> sendEvent(CompanyDetailsReducer.Event.Error.ProjectDeleteError)
                }
            }
        }
    }

    private fun toggleProjectPin(projectId: Int) {
        viewModelScope.launch {
            val isCurrentlyPinned = state.value.pinnedProjects.any { it.id == projectId }
            val operation =
                if (isCurrentlyPinned) unpinProjectUseCase(projectId) else pinProjectUseCase(
                    projectId
                )
            operation.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        loadPinnedProjects()
                        sendEvent(CompanyDetailsReducer.Event.ProjectAction.PinCompleted(!isCurrentlyPinned))
                    }

                    is Resource.Error -> sendEvent(CompanyDetailsReducer.Event.Error.ProjectPinError)
                    is Resource.Loading -> Unit
                }
            }
        }
    }
}