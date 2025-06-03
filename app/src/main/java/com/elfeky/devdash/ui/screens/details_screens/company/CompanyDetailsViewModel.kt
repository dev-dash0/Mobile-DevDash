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
    CompanyDetailsReducer.initialState(),
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
            is CompanyDetailsReducer.Event.UpdateIsLoading,
            is CompanyDetailsReducer.Event.DismissDialogClicked,
            is CompanyDetailsReducer.Event.UpdateCompany,
            is CompanyDetailsReducer.Event.UpdatePinnedStatus,
            is CompanyDetailsReducer.Event.UpdateUserId,
            is CompanyDetailsReducer.Event.UpdateProjects,
            is CompanyDetailsReducer.Event.UpdatePinnedProjects,
            is CompanyDetailsReducer.Event.SetProjectsLoading -> sendEvent(event)

            else -> sendEventForEffect(event)
        }
    }

    private fun observeEffects() {
        viewModelScope.launch {
            internalEffect.collect { effect ->
                when (effect) {
                    CompanyDetailsReducer.Effect.TriggerReloadAllData -> loadAllCompanyData()
                    CompanyDetailsReducer.Effect.TriggerDeleteCompany -> deleteCompany()
                    is CompanyDetailsReducer.Effect.TriggerUpdateCompany -> updateCompany(
                        effect.companyUiModel
                    )

                    CompanyDetailsReducer.Effect.TriggerToggleCompanyPin -> toggleTenantPin()
                    is CompanyDetailsReducer.Effect.TriggerAddProject -> addProject(effect.projectRequest)
                    is CompanyDetailsReducer.Effect.TriggerDeleteProject -> deleteProject(
                        effect.projectId
                    )

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
            sendEvent(CompanyDetailsReducer.Event.UpdateIsLoading(true))
            loadCompany()
            loadPinnedStatus()
            loadUserProfile()
            sendEvent(CompanyDetailsReducer.Event.UpdateIsLoading(false))

            sendEvent(CompanyDetailsReducer.Event.SetProjectsLoading(true))
            loadCompanyProjects()
            loadPinnedProjects()
            sendEvent(CompanyDetailsReducer.Event.SetProjectsLoading(false))
        }
    }

    private suspend fun loadCompany() {
        getTenantByIdUseCase(companyId).collect { result ->
            when (result) {
                is Resource.Success -> sendEvent(
                    CompanyDetailsReducer.Event.UpdateCompany(
                        result.data!!
                    )
                )

                is Resource.Error -> sendEventForEffect(
                    CompanyDetailsReducer.Event.Error.CompanyLoadError
                )

                is Resource.Loading -> Unit
            }
        }
    }

    private suspend fun loadPinnedStatus() {
        getPinnedTenantsUseCase().collect { result ->
            when (result) {
                is Resource.Success -> sendEvent(
                    CompanyDetailsReducer.Event.UpdatePinnedStatus(
                        result.data?.any { tenant -> tenant.id == companyId } == true
                    )
                )

                is Resource.Error -> sendEventForEffect(
                    CompanyDetailsReducer.Event.Error.PinnedTenantsLoadError
                )

                else -> Unit
            }
        }
    }

    private suspend fun loadUserProfile() {
        getUserProfileUseCase().collect { result ->
            when (result) {
                is Resource.Success -> sendEvent(
                    CompanyDetailsReducer.Event.UpdateUserId(
                        result.data?.id!!
                    )
                )

                is Resource.Error -> sendEventForEffect(
                    CompanyDetailsReducer.Event.Error.UserProfileLoadError
                )

                is Resource.Loading -> Unit
            }
        }
    }

    private suspend fun loadCompanyProjects() {
        getTenantProjectsUseCase(companyId).collect { result ->
            when (result) {
                is Resource.Success -> sendEvent(
                    CompanyDetailsReducer.Event.UpdateProjects(
                        result.data ?: emptyList()
                    )
                )

                is Resource.Error -> sendEventForEffect(
                    CompanyDetailsReducer.Event.Error.ProjectsLoadError
                )

                is Resource.Loading -> Unit
            }
        }
    }

    private suspend fun loadPinnedProjects() {
        getPinnedProjectsUseCase().collect { result ->
            when (result) {
                is Resource.Success -> sendEvent(
                    CompanyDetailsReducer.Event.UpdatePinnedProjects(
                        result.data ?: emptyList()
                    )
                )

                is Resource.Error -> sendEventForEffect(
                    CompanyDetailsReducer.Event.Error.PinnedProjectsLoadError
                )

                is Resource.Loading -> Unit
            }
        }
    }

    private fun deleteCompany() {
        viewModelScope.launch {
            deleteTenantUseCase(companyId).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> sendEventForEffect(CompanyDetailsReducer.Event.CompanyDeletionCompleted)
                    is Resource.Error -> sendEventForEffect(
                        CompanyDetailsReducer.Event.Error.CompanyDeleteError
                    )
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
                    is Resource.Success -> sendEventForEffect(CompanyDetailsReducer.Event.CompanyUpdateCompleted)
                    is Resource.Error -> sendEventForEffect(
                        CompanyDetailsReducer.Event.Error.CompanyUpdateError
                    )
                }
            }
        }
    }

    private fun toggleTenantPin() {
        viewModelScope.launch {
            val isCurrentlyPinned = state.value.isPinned
            val operation =
                if (isCurrentlyPinned) unpinTenantUseCase(companyId) else pinTenantUseCase(
                    companyId
                )
            operation.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        sendEventForEffect(
                            CompanyDetailsReducer.Event.UpdatePinnedStatus(!isCurrentlyPinned)
                        )
                        sendEventForEffect(CompanyDetailsReducer.Event.PinOperationCompleted)
                    }

                    is Resource.Error -> sendEventForEffect(
                        CompanyDetailsReducer.Event.Error.CompanyPinError
                    )

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
                        sendEventForEffect(
                            CompanyDetailsReducer.Event.ProjectAdded(
                                it
                            )
                        )
                    }

                    is Resource.Error -> sendEventForEffect(
                        CompanyDetailsReducer.Event.Error.ProjectAddError
                    )

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
                        CompanyDetailsReducer.Event.SetProjectsLoading(
                            true
                        )
                    )

                    is Resource.Success -> sendEventForEffect(CompanyDetailsReducer.Event.ProjectDeletionCompleted)
                    is Resource.Error -> sendEventForEffect(
                        CompanyDetailsReducer.Event.Error.ProjectDeleteError
                    )
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
                        sendEventForEffect(CompanyDetailsReducer.Event.PinOperationCompleted)
                    }

                    is Resource.Error -> sendEventForEffect(
                        CompanyDetailsReducer.Event.Error.ProjectPinError
                    )

                    is Resource.Loading -> Unit
                }
            }
        }
    }
}