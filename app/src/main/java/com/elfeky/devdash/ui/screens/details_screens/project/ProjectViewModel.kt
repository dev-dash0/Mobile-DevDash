package com.elfeky.devdash.ui.screens.details_screens.project

import android.util.Log
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
    private val getPinnedItemsUseCase: GetPinnedItemsUseCase,
    private val pinItemUseCase: PinItemUseCase,
    private val unpinItemUseCase: UnpinItemUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProjectState())
    val state: StateFlow<ProjectState> = _state.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ProjectState())

    private fun getAllProjects(tenantId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getAllProjectsUseCase(tenantId).onEach { result ->
                when (result) {
                    is Resource.Loading -> Unit

                    is Resource.Success -> _state.update {
                        it.copy(
                            projects = result.data ?: emptyList()
                        )
                    }

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

    fun addProject(project: ProjectRequest, tenantId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addProjectUseCase(project, tenantId).onEach { result ->
                when (result) {
                    is Resource.Loading -> Unit

                    is Resource.Success -> getAllProjects(tenantId)
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

    fun deleteProject(id: Int, tenantId: Int) {
        viewModelScope.launch {
            deleteProjectUseCase(id).onEach { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(projectDeleted = false) }
                    is Resource.Success -> refreshUi(tenantId)

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

    fun updateProject(editedProject: UpdateProjectRequest, id: Int, tenantId: Int) {
        viewModelScope.launch {
            updateProjectUseCase(id, editedProject).onEach { result ->
                when (result) {
                    is Resource.Loading -> Unit

                    is Resource.Success -> getAllProjects(tenantId)

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

    private fun getPinnedProjects() {
        viewModelScope.launch {
            getPinnedItemsUseCase().onEach { result ->
                when (result) {
                    is Resource.Loading -> Unit

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                pinnedProjects = result.data?.projects ?: emptyList()
                            )
                        }
                        Log.d("ProjectViewModel", result.data?.toString() ?: "null")
                    }

                    is Resource.Error -> _state.update {
                        it.copy(error = result.message ?: "Un expected error happened")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun pinProject(id: Int) {
        viewModelScope.launch {
            pinItemUseCase(id, "project").onEach { result ->
                when (result) {
                    is Resource.Loading -> Unit

                    is Resource.Success -> getPinnedProjects()

                    is Resource.Error -> {
                        _state.update { it.copy(error = result.message!!) }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun unpinProject(id: Int) {
        viewModelScope.launch {
            unpinItemUseCase(id, "project").onEach { result ->
                when (result) {
                    is Resource.Loading -> Unit

                    is Resource.Success -> getPinnedProjects()

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

    fun isPinned(id: Int): Boolean = _state.value.pinnedProjects.map { it.id }.contains(id)

    fun refreshUi(tenantId: Int) {
        getAllProjects(tenantId)
        getPinnedProjects()
    }
}