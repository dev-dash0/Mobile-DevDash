package com.elfeky.devdash.ui.screens.details_screens.company

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CompanyDetailsViewModel.Factory::class)
class CompanyDetailsViewModel @AssistedInject constructor(
    @Assisted val companyId: Int,
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
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(tenantId: Int): CompanyDetailsViewModel
    }

    private val _state = MutableStateFlow(CompanyDetailsUiState(companyId))
    val state: StateFlow<CompanyDetailsUiState> = _state.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CompanyDetailsUiState(companyId)
        )

    init {
        refreshUi()
    }

    private fun getTenantData() {
        viewModelScope.launch {
            getTenantByIdUseCase(companyId).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> _state.update {
                        result.data?.let { data ->
                            it.copy(tenant = data, isLoading = false, error = null)
                        } ?: it.copy(
                            isLoading = false,
                            error = "Unexpected error occurred loading tenant data"
                        )
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Failed to load tenant data"
                        )
                    }
                }
            }
        }
    }

    private fun isPinnedTenant() {
        viewModelScope.launch {
            getPinnedTenantsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> _state.update {
                        it.copy(isPinned = result.data?.any { tenant -> tenant.id == companyId } == true,
                            error = null)
                    }

                    is Resource.Error -> _state.update {
                        it.copy(error = result.message ?: "Failed to check if tenant is pinned")
                    }
                }
            }
        }
    }

    private fun getUserId() {
        viewModelScope.launch {
            getUserProfileUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> _state.update {
                        result.data?.let { user -> it.copy(userId = user.id, error = null) }
                            ?: it.copy(error = "Unexpected error occurred loading user profile")
                    }

                    is Resource.Error -> _state.update {
                        it.copy(error = result.message ?: "Failed to load user profile")
                    }
                }
            }
        }
    }

    private fun getProjects() {
        viewModelScope.launch {
            getTenantProjectsUseCase(companyId).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(projectsLoading = true) }
                    is Resource.Success -> _state.update {
                        it.copy(
                            projects = result.data ?: emptyList(),
                            projectsLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            projectsLoading = false,
                            error = result.message ?: "Failed to load projects"
                        )
                    }
                }
            }
        }
    }

    private fun getPinnedProjects() {
        viewModelScope.launch {
            getPinnedProjectsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> _state.update {
                        it.copy(pinnedProjects = result.data ?: emptyList(), error = null)
                    }

                    is Resource.Error -> _state.update {
                        it.copy(error = result.message ?: "Error fetching pinned projects")
                    }
                }
            }
        }
    }

    fun deleteCompany() {
        viewModelScope.launch {
            deleteTenantUseCase(companyId).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update {
                        it.copy(
                            isLoading = true,
                            deleteErrorMessage = null
                        )
                    }

                    is Resource.Success -> _state.update {
                        it.copy(
                            isLoading = false,
                            isDeleted = true,
                            error = null
                        )
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Error deleting company",
                            deleteErrorMessage = result.message ?: "Error deleting company",
                            isDeleted = false
                        )
                    }
                }
            }
        }
    }

    fun updateCompany(companyUiModel: CompanyUiModel) {
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
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        _state.update { it.copy(isLoading = false, error = null) }
                        getTenantData()
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Error updating company"
                        )
                    }
                }
            }
        }
    }

    fun pinTenant() {
        viewModelScope.launch {
            if (_state.value.isPinned) {
                unpinTenantUseCase(companyId).collect { result ->
                    when (result) {
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            _state.update { it.copy(isPinned = false, error = null) }
                        }

                        is Resource.Error -> _state.update {
                            it.copy(
                                error = result.message ?: "Error unpinning company"
                            )
                        }
                    }
                }
            } else {
                pinTenantUseCase(companyId).collect { result ->
                    when (result) {
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            _state.update { it.copy(isPinned = true, error = null) }
                        }

                        is Resource.Error -> _state.update {
                            it.copy(
                                error = result.message ?: "Error pinning company"
                            )
                        }
                    }
                }
            }
        }
    }

    fun addProject(projectRequest: ProjectRequest) {
        viewModelScope.launch {
            addProjectUseCase(projectRequest, companyId).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(projectsLoading = true) }
                    is Resource.Success -> {
                        _state.update { it.copy(projectsLoading = false, error = null) }
                        getProjects()
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            projectsLoading = false,
                            error = result.message ?: "Error adding project"
                        )
                    }
                }
            }
        }
    }

    fun deleteProject(id: Int) {
        viewModelScope.launch {
            deleteProjectUseCase(id).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(projectsLoading = true) }
                    is Resource.Success -> {
                        _state.update { it.copy(projectsLoading = false, error = null) }
                        getProjects()
                        getPinnedProjects()
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            projectsLoading = false,
                            error = result.message ?: "Error deleting project"
                        )
                    }
                }
            }
        }
    }

    fun pinProject(id: Int) {
        viewModelScope.launch {
            val isCurrentlyPinned = _state.value.pinnedProjects.any { it.id == id }
            if (isCurrentlyPinned) {
                unpinProjectUseCase(id).collect { result ->
                    when (result) {
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            _state.update { it.copy(error = null) }
                            getPinnedProjects()
                        }

                        is Resource.Error -> _state.update {
                            it.copy(
                                error = result.message ?: "Error unpinning project"
                            )
                        }
                    }
                }
            } else {
                pinProjectUseCase(id).collect { result ->
                    when (result) {
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            _state.update { it.copy(error = null) }
                            getPinnedProjects()
                        }

                        is Resource.Error -> _state.update {
                            it.copy(
                                error = result.message ?: "Error pinning project"
                            )
                        }
                    }
                }
            }
        }
    }

    fun removeMember(id: Int) {
        // TODO: Implement member removal logic
    }

    fun refreshUi() {
        getTenantData()
        isPinnedTenant()
        getProjects()
        getPinnedProjects()
        getUserId()
    }

    fun clearError() {
        _state.update { it.copy(error = null, deleteErrorMessage = null) }
    }
}