package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.model.project.UpdateProjectRequest
import com.elfeky.domain.usecase.pin.GetPinnedItemsUseCase
import com.elfeky.domain.usecase.pin.PinItemUseCase
import com.elfeky.domain.usecase.pin.UnpinItemUseCase
import com.elfeky.domain.usecase.project.AddProjectUseCase
import com.elfeky.domain.usecase.project.DeleteProjectUseCase
import com.elfeky.domain.usecase.project.GetAllProjectsUseCase
import com.elfeky.domain.usecase.project.UpdateProjectUseCase
import com.elfeky.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ProjectViewModel.Factory::class)
class ProjectViewModel @AssistedInject constructor(
    @Assisted val tenantId: Int,
    private val addProjectUseCase: AddProjectUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val getPinnedItemsUseCase: GetPinnedItemsUseCase,
    private val pinItemUseCase: PinItemUseCase,
    private val unpinItemUseCase: UnpinItemUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(
            tenantId: Int,
        ): ProjectViewModel
    }

    private val _state = MutableStateFlow(ProjectState(tenantId))
    val state: StateFlow<ProjectState> = _state.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProjectState(tenantId))

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.update {
            it.copy(error = throwable.message ?: "An unexpected error occurred")
        }
    }

    init {
        refreshUi()
    }

    private fun getAllProjects() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            getAllProjectsUseCase(tenantId)
                .catch { throwable ->
                    _state.update {
                        it.copy(error = throwable.message ?: "Error fetching projects")
                    }
                }
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            // Handle loading state if needed
                        }

                        is Resource.Success -> _state.update {
                            it.copy(projects = result.data ?: emptyList(), error = null)
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(error = result.message ?: "Error fetching projects")
                            }
                        }
                    }
                }
        }
    }

    fun addProject(project: ProjectRequest) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            addProjectUseCase(project, tenantId)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            _state.update { it.copy(error = null) }
                            getAllProjects()
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(error = result.message ?: "Error adding project")
                            }
                        }
                    }
                }
        }
    }

    fun deleteProject(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            _state.update {
                it.copy(projects = it.projects.filter { project -> project.id != id })
            }

            deleteProjectUseCase(id)
                .catch { throwable ->
                    refreshUi()
                    _state.update {
                        it.copy(error = throwable.message ?: "Error deleting project")
                    }
                }
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> Unit
                        is Resource.Success -> _state.update { it.copy(error = null) }
                        is Resource.Error -> {
                            // Error handled in catch block
                        }
                    }
                }
        }
    }

    fun updateProject(editedProject: UpdateProjectRequest, id: Int) {
        viewModelScope.launch(exceptionHandler) {
            updateProjectUseCase(id, editedProject)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            _state.update { it.copy(error = null) }
                            getAllProjects()
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(error = result.message ?: "Error updating project")
                            }
                        }
                    }
                }
        }
    }

    private fun getPinnedProjects() {
        viewModelScope.launch(exceptionHandler) {
            getPinnedItemsUseCase()
                .catch { throwable ->
                    _state.update {
                        it.copy(error = throwable.message ?: "Error fetching pinned projects")
                    }
                }
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    pinnedProjects = result.data?.projects ?: emptyList(),
                                    error = null
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(error = result.message ?: "Error fetching pinned projects")
                            }
                        }
                    }
                }
        }
    }

    fun pinProject(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            _state.update {
                val projectToPin = it.projects.find { p -> p.id == id }
                if (projectToPin != null && !it.pinnedProjects.any { p -> p.id == id }) {
                    it.copy(pinnedProjects = it.pinnedProjects + projectToPin)
                } else {
                    it
                }
            }

            pinItemUseCase(id, "project")
                .catch { throwable ->
                    getPinnedProjects()
                    _state.update { it.copy(error = throwable.message ?: "Error pinning project") }
                }
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            _state.update { it.copy(error = null) }
                        }

                        is Resource.Error -> {
                            // Error handled in catch block
                        }
                    }
                }
        }
    }

    fun unpinProject(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            _state.update {
                it.copy(pinnedProjects = it.pinnedProjects.filter { project -> project.id != id })
            }

            unpinItemUseCase(id, "project")
                .catch { throwable ->
                    getPinnedProjects()
                    _state.update {
                        it.copy(
                            error = throwable.message ?: "Error unpinning project"
                        )
                    }
                }
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            _state.update { it.copy(error = null) }
                        }

                        is Resource.Error -> {
                            // Error handled in catch block
                        }
                    }
                }
        }
    }

    fun openProjectDialog(project: Project?) {
        _state.update { it.copy(showProjectDialog = true, selectedProject = project, error = null) }
    }

    fun closeProjectDialog() {
        _state.update { it.copy(showProjectDialog = false, selectedProject = null, error = null) }
    }

    fun isPinned(id: Int): Boolean = _state.value.pinnedProjects.any { it.id == id }

    fun refreshUi() {
        getAllProjects()
        getPinnedProjects()
    }
}