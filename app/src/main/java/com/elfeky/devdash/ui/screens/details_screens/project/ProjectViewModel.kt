package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.usecase.project.AddProjectUseCase
import com.elfeky.domain.usecase.project.GetAllProjectsUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val addProjectUseCase: AddProjectUseCase,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProjectState())
    val state: StateFlow<ProjectState> = _state.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ProjectState())

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> =
        _event.asSharedFlow().shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun addProject(project: ProjectRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            addProjectUseCase(project).onEach { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isCreatingProject = true) }

                    is Resource.Success -> {
                        _state.update { it.copy(isCreatingProject = false) }
                        _event.emit(Event.ProjectCreated)
                        _event.emit(Event.HideCreateDialog)
                    }

                    is Resource.Error -> {
                        _state.update { it.copy(isCreatingProject = false) }
                        _event.emit(Event.ShowError(result.message ?: "Unknown error occurred"))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getAllProjects(tenantId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getAllProjectsUseCase(tenantId).onEach { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }

                    is Resource.Success -> _state.update {
                        it.copy(isLoading = false, projects = result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _event.emit(Event.ShowError(result.message ?: "Unknown error occurred"))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}