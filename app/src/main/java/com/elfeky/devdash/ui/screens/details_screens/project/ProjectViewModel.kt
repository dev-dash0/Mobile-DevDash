package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.devdash.ui.screens.details_screens.project.model.ProjectState
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.model.project.UpdateProjectRequest
import com.elfeky.domain.usecase.project.AddProjectUseCase
import com.elfeky.domain.usecase.project.DeleteProjectUseCase
import com.elfeky.domain.usecase.project.GetAllProjectsUseCase
import com.elfeky.domain.usecase.project.UpdateProjectUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val addProjectUseCase: AddProjectUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProjectState())
    val state: StateFlow<ProjectState> = _state.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ProjectState())

    fun addProject(project: ProjectRequest, tenantId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addProjectUseCase(project, tenantId).onEach { result ->
                when (result) {
                    is Resource.Loading -> Unit

                    is Resource.Success -> _state.update { it.copy(updateUi = true) }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message ?: "Un expected error happened"
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getAllProjects(tenantId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getAllProjectsUseCase(tenantId).onEach { result ->
                when (result) {
                    is Resource.Loading -> Unit

                    is Resource.Success ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                updateUi = false,
                                projects = result.data ?: emptyList()
                            )
                        }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Un expected error happened"
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun deleteProject(id: Int) {
        viewModelScope.launch {
            deleteProjectUseCase(id).onEach { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(projectDeleted = false) }
                    is Resource.Success -> _state.update {
                        it.copy(
                            updateUi = true,
                            projectDeleted = true
                        )
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            error = result.message ?: "Un expected error happened",
                            projectDeleted = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updateProject(editedProject: UpdateProjectRequest, id: Int) {
        viewModelScope.launch {
            updateProjectUseCase(id, editedProject).onEach { result ->
                when (result) {
                    is Resource.Loading -> Unit

                    is Resource.Success -> _state.update { it.copy(updateUi = true) }

                    is Resource.Error -> {
                        _state.update { it.copy(error = result.message!!) }
                    }

                }
            }.launchIn(viewModelScope)
        }
    }

    fun openProjectDialog(project: Project?) {
        _state.update { it.copy(showProjectDialog = true, selectedProject = project) }
    }

    fun closeProjectDialog() {
        _state.update { it.copy(showProjectDialog = false, selectedProject = null) }
    }
}