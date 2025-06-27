package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.lifecycle.viewModelScope
import com.elfeky.devdash.ui.base.BaseViewModel
import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.model.project.UpdateProjectRequest
import com.elfeky.domain.model.sprint.SprintRequest
import com.elfeky.domain.usecase.backlog.CreateBacklogIssueUseCase
import com.elfeky.domain.usecase.backlog.GetBacklogIssuesUseCase
import com.elfeky.domain.usecase.issue.DeleteIssueUseCase
import com.elfeky.domain.usecase.issue.GetIssueByIdUseCase
import com.elfeky.domain.usecase.pin.get.GetPinnedIssuesUseCase
import com.elfeky.domain.usecase.pin.get.GetPinnedProjectsUseCase
import com.elfeky.domain.usecase.pin.get.GetPinnedSprintsUseCase
import com.elfeky.domain.usecase.pin.pin.PinIssueUseCase
import com.elfeky.domain.usecase.pin.pin.PinProjectUseCase
import com.elfeky.domain.usecase.pin.pin.PinSprintUseCase
import com.elfeky.domain.usecase.pin.unpin.UnpinIssueUseCase
import com.elfeky.domain.usecase.pin.unpin.UnpinProjectUseCase
import com.elfeky.domain.usecase.pin.unpin.UnpinSprintUseCase
import com.elfeky.domain.usecase.project.DeleteProjectUseCase
import com.elfeky.domain.usecase.project.GetProjectByIdUseCase
import com.elfeky.domain.usecase.project.UpdateProjectUseCase
import com.elfeky.domain.usecase.sprint.CreateSprintUseCase
import com.elfeky.domain.usecase.sprint.DeleteSprintUseCase
import com.elfeky.domain.usecase.sprint.GetProjectSprintsUseCase
import com.elfeky.domain.usecase.sprint_issue.CreateSprintIssueUseCase
import com.elfeky.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ProjectDetailsViewModel.Factory::class)
class ProjectDetailsViewModel @AssistedInject constructor(
    @Assisted private val projectId: Int,
    private val getProjectByIdUseCase: GetProjectByIdUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val getPinnedProjectsUseCase: GetPinnedProjectsUseCase,
    private val getPinnedIssuesUseCase: GetPinnedIssuesUseCase,
    private val getPinnedSprintsUseCase: GetPinnedSprintsUseCase,
    private val getProjectSprintsUseCase: GetProjectSprintsUseCase,
    private val createSprintUseCase: CreateSprintUseCase,
    private val deleteSprintUseCase: DeleteSprintUseCase,
    private val createBacklogIssueUseCase: CreateBacklogIssueUseCase,
    private val deleteIssueUseCase: DeleteIssueUseCase,
    private val pinProjectUseCase: PinProjectUseCase,
    private val unpinProjectUseCase: UnpinProjectUseCase,
    private val pinSprintUseCase: PinSprintUseCase,
    private val unpinSprintUseCase: UnpinSprintUseCase,
    private val pinIssueUseCase: PinIssueUseCase,
    private val unpinIssueUseCase: UnpinIssueUseCase,
    private val getBacklogIssuesUseCase: GetBacklogIssuesUseCase,
    private val createSprintIssueUseCase: CreateSprintIssueUseCase,
    private val getIssueByIdUseCase: GetIssueByIdUseCase,
) : BaseViewModel<ProjectDetailsReducer.State, ProjectDetailsReducer.Event, ProjectDetailsReducer.Effect>(
    ProjectDetailsReducer.initialState,
    ProjectDetailsReducer()
) {
    @AssistedFactory
    interface Factory {
        fun create(projectId: Int): ProjectDetailsViewModel
    }

    init {
        observeEffects()
        loadAllProjectData()
    }

    fun onEvent(event: ProjectDetailsReducer.Event) {
        when (event) {
            is ProjectDetailsReducer.Event.Update -> sendEvent(event)

            else -> sendEventForEffect(event)
        }
    }

    private fun observeEffects() {
        viewModelScope.launch {
            internalEffect.collect { effect ->
                when (effect) {
                    ProjectDetailsReducer.Effect.TriggerReloadAllData -> loadAllProjectData()
                    ProjectDetailsReducer.Effect.TriggerLoadSprints -> loadSprints()
                    ProjectDetailsReducer.Effect.TriggerDeleteProject -> {
                        deleteProject()
                        sendUiEffect(ProjectDetailsReducer.Effect.DismissDialog)
                    }

                    is ProjectDetailsReducer.Effect.TriggerUpdateProject -> {
                        updateProject(effect.projectRequest)
                        sendUiEffect(ProjectDetailsReducer.Effect.DismissDialog)
                    }

                    ProjectDetailsReducer.Effect.TriggerToggleProjectPin -> toggleProjectPin()
                    is ProjectDetailsReducer.Effect.TriggerAddSprint -> {
                        addSprint(effect.sprint)
                        sendUiEffect(ProjectDetailsReducer.Effect.DismissDialog)
                    }

                    is ProjectDetailsReducer.Effect.TriggerDeleteSprint -> {
                        deleteSprint(effect.sprintId)
                        sendUiEffect(ProjectDetailsReducer.Effect.DismissDialog)
                    }

                    is ProjectDetailsReducer.Effect.TriggerAddIssue -> {
                        createIssue(effect.issueBacklog)
                        sendUiEffect(ProjectDetailsReducer.Effect.DismissDialog)
                    }

                    is ProjectDetailsReducer.Effect.TriggerDeleteIssue -> {
                        deleteIssue(effect.issueId)
                        sendUiEffect(ProjectDetailsReducer.Effect.DismissDialog)
                    }

                    is ProjectDetailsReducer.Effect.TriggerToggleSprintPin -> toggleSprintPin(effect.sprintId)
                    is ProjectDetailsReducer.Effect.TriggerToggleIssuePin -> toggleIssuePin(effect.issueId)
                    ProjectDetailsReducer.Effect.TriggerLoadBacklogIssues -> loadBacklogIssues()
                    is ProjectDetailsReducer.Effect.AddSprintIssue -> createSprintIssue(
                        effect.issueId,
                        effect.sprintId
                    )

                    is ProjectDetailsReducer.Effect.ShowSnackbar,
                    is ProjectDetailsReducer.Effect.ShowDialog,
                    ProjectDetailsReducer.Effect.DismissDialog,
                    ProjectDetailsReducer.Effect.NavigateBack,
                    is ProjectDetailsReducer.Effect.NavigateToSprintDetails -> sendUiEffect(effect)
                }
            }
        }
    }

    private fun loadAllProjectData() {
        viewModelScope.launch {
            sendEvent(ProjectDetailsReducer.Event.Update.IsLoading(true))
            runCatching {
                loadProject()
                loadPinnedProjectStatus()
                loadSprints()
                loadPinnedSprints()
                loadPinnedIssues()
                loadBacklogIssues()
            }.onSuccess {
                sendEvent(ProjectDetailsReducer.Event.Update.IsLoading(false))
            }.onFailure {
                sendEvent(ProjectDetailsReducer.Event.Update.IsLoading(false))
                sendUiEffect(ProjectDetailsReducer.Effect.ShowSnackbar("Failed to load all project data."))
            }
        }
    }

    private suspend fun <T> collectResource(
        resourceFlow: Flow<Resource<T>>,
        successEvent: (T) -> ProjectDetailsReducer.Event.Update,
        errorEvent: ProjectDetailsReducer.Event.Error
    ) {
        resourceFlow.collect { result ->
            when (result) {
                is Resource.Success -> result.data?.let { sendEvent(successEvent(it)) }
                is Resource.Error -> sendEvent(errorEvent)
                is Resource.Loading -> Unit
            }
        }
    }

    private suspend fun loadProject() {
        collectResource(
            resourceFlow = getProjectByIdUseCase(projectId),
            successEvent = { ProjectDetailsReducer.Event.Update.ProjectInfo(it) },
            errorEvent = ProjectDetailsReducer.Event.Error.ProjectLoadError
        )
    }

    private suspend fun loadPinnedProjectStatus() {
        getPinnedProjectsUseCase().collect { result ->
            when (result) {
                is Resource.Success -> sendEvent(
                    ProjectDetailsReducer.Event.Update.PinnedStatus(
                        result.data?.any { project -> project.id == projectId } == true
                    )
                )

                is Resource.Error -> sendEvent(ProjectDetailsReducer.Event.Error.PinnedProjectLoadError)
                else -> Unit
            }
        }
    }

    private fun loadSprints() {
        sendEvent(ProjectDetailsReducer.Event.Update.SprintsFlow(getProjectSprintsUseCase(projectId)))
    }

    private suspend fun loadPinnedSprints() {
        collectResource(
            resourceFlow = getPinnedSprintsUseCase(),
            successEvent = { ProjectDetailsReducer.Event.Update.PinnedSprints(it) },
            errorEvent = ProjectDetailsReducer.Event.Error.PinnedSprintsLoadError
        )
    }

    private suspend fun loadPinnedIssues() {
        collectResource(
            resourceFlow = getPinnedIssuesUseCase(),
            successEvent = { ProjectDetailsReducer.Event.Update.PinnedIssues(it) },
            errorEvent = ProjectDetailsReducer.Event.Error.PinnedIssuesLoadError
        )
    }

    private fun loadBacklogIssues() {
        sendEvent(
            ProjectDetailsReducer.Event.Update.BacklogIssuesFlow(
                getBacklogIssuesUseCase(
                    projectId
                )
            )
        )
    }

    private fun deleteProject() {
        viewModelScope.launch {
            deleteProjectUseCase(projectId).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> sendEvent(ProjectDetailsReducer.Event.ProjectAction.DeletionCompleted)
                    is Resource.Error -> sendEvent(ProjectDetailsReducer.Event.Error.ProjectDeleteError)
                }
            }
        }
    }

    private fun updateProject(projectRequest: ProjectRequest) {
        val request = UpdateProjectRequest(
            description = projectRequest.description,
            endDate = projectRequest.endDate,
            id = projectId,
            isPinned = state.value.isPinned,
            name = projectRequest.name,
            priority = projectRequest.priority,
            startDate = projectRequest.startDate,
            status = projectRequest.status,
        )
        viewModelScope.launch {
            updateProjectUseCase(projectId, request).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> sendEvent(ProjectDetailsReducer.Event.ProjectAction.UpdateCompleted)
                    is Resource.Error -> sendEvent(ProjectDetailsReducer.Event.Error.ProjectUpdateError)
                }
            }
        }
    }

    private fun toggleProjectPin() {
        viewModelScope.launch {
            val isCurrentlyPinned = state.value.isPinned
            val operation =
                if (isCurrentlyPinned) unpinProjectUseCase(projectId) else pinProjectUseCase(
                    projectId
                )
            operation.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        sendEvent(ProjectDetailsReducer.Event.Update.PinnedStatus(!isCurrentlyPinned))
                        sendEvent(ProjectDetailsReducer.Event.ProjectAction.PinCompleted(!isCurrentlyPinned))
                    }

                    is Resource.Error -> sendEvent(ProjectDetailsReducer.Event.Error.ProjectPinError)
                    is Resource.Loading -> Unit
                }
            }
        }
    }

    private fun addSprint(sprintRequest: SprintRequest) {
        viewModelScope.launch {
            createSprintUseCase(projectId, sprintRequest).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> sendEvent(ProjectDetailsReducer.Event.SprintAction.CreateCompleted)
                    is Resource.Error -> sendEvent(ProjectDetailsReducer.Event.Error.SprintAddError)
                }
            }
        }
    }

    private fun deleteSprint(sprintId: Int) {
        viewModelScope.launch {
            deleteSprintUseCase(sprintId).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> sendEventForEffect(ProjectDetailsReducer.Event.SprintAction.DeletionCompleted)
                    is Resource.Error -> sendEventForEffect(ProjectDetailsReducer.Event.Error.SprintDeleteError)
                }
            }
        }
    }

    private fun toggleSprintPin(sprintId: Int) {
        viewModelScope.launch {
            val isCurrentlyPinned = state.value.pinnedSprints.any { it.id == sprintId }
            val operation =
                if (isCurrentlyPinned) unpinSprintUseCase(sprintId) else pinSprintUseCase(sprintId)
            operation.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        loadPinnedSprints()
                        sendEvent(ProjectDetailsReducer.Event.SprintAction.PinCompleted(!isCurrentlyPinned))
                    }

                    is Resource.Error -> sendEvent(ProjectDetailsReducer.Event.Error.SprintPinError)
                    is Resource.Loading -> Unit
                }
            }
        }
    }

    private fun createIssue(issueRequest: IssueFormFields) {
        viewModelScope.launch {
            createBacklogIssueUseCase(
                projectId = projectId,
                attachmentFile = null,
                attachmentMediaType = null,
                priority = issueRequest.priority,
                status = issueRequest.status,
                title = issueRequest.title,
                type = issueRequest.type,
                description = issueRequest.description ?: "",
                isBacklog = issueRequest.isBacklog,
                startDate = issueRequest.startDate ?: "",
                deadline = issueRequest.deadline ?: "",
                deliveredDate = issueRequest.deliveredDate ?: "",
                lastUpdate = issueRequest.lastUpdate ?: "",
                labels = issueRequest.labels ?: ""
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        sendEvent(ProjectDetailsReducer.Event.IssueAction.CreateCompleted)
                    }

                    is Resource.Error -> sendEvent(ProjectDetailsReducer.Event.Error.IssueAddError)
                    is Resource.Loading -> Unit
                }
            }
        }
    }

    private fun deleteIssue(issueId: Int) {
        viewModelScope.launch {
            deleteIssueUseCase(issueId).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> sendEvent(ProjectDetailsReducer.Event.IssueAction.DeletionCompleted)
                    is Resource.Error -> sendEvent(ProjectDetailsReducer.Event.Error.IssueDeleteError)
                }
            }
        }
    }

    private fun toggleIssuePin(issueId: Int) {
        viewModelScope.launch {
            val isCurrentlyPinned = state.value.pinnedIssues.any { it.id == issueId }
            val operation =
                if (isCurrentlyPinned) unpinIssueUseCase(issueId) else pinIssueUseCase(issueId)
            operation.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        loadPinnedIssues()
                        sendEvent(ProjectDetailsReducer.Event.IssueAction.PinCompleted(!isCurrentlyPinned))
                    }

                    is Resource.Error -> sendEvent(ProjectDetailsReducer.Event.Error.IssuePinError)
                    is Resource.Loading -> Unit
                }
            }
        }
    }

    private fun createSprintIssue(issueId: Int, sprintId: Int) {
        viewModelScope.launch {
            getIssueByIdUseCase(issueId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val issueToMove = result.data
                        if (issueToMove == null) {
                            sendUiEffect(ProjectDetailsReducer.Effect.ShowSnackbar("Failed to find issue to move."))
                            return@collect
                        }
                        IssueFormFields(
                            priority = issueToMove.priority,
                            status = issueToMove.status,
                            title = issueToMove.title,
                            type = issueToMove.type,
                            labels = issueToMove.labels,
                            description = issueToMove.description,
                            isBacklog = false,
                            startDate = issueToMove.startDate,
                            deadline = issueToMove.deadline,
                            deliveredDate = issueToMove.deliveredDate,
                            lastUpdate = issueToMove.lastUpdate
                        )
                        createSprintIssueUseCase(
                            sprintId = sprintId,
                            priority = issueToMove.priority,
                            status = issueToMove.status,
                            title = issueToMove.title,
                            type = issueToMove.type,
                            description = issueToMove.description ?: "",
                            isBacklog = false,
                            startDate = issueToMove.startDate,
                            deadline = issueToMove.deadline,
                            deliveredDate = issueToMove.deliveredDate ?: "",
                            lastUpdate = issueToMove.lastUpdate,
                            labels = issueToMove.labels,
                            attachmentFile = null,
                            attachmentMediaType = "file",
                        ).collect { createResult ->
                            when (createResult) {
                                is Resource.Success -> {
                                    sendEvent(ProjectDetailsReducer.Event.IssueAction.MovedToSprintCompleted)
                                    loadBacklogIssues()
                                    loadSprints()
                                }

                                is Resource.Error -> sendEvent(ProjectDetailsReducer.Event.Error.IssueMoveToSprintError)
                                is Resource.Loading -> Unit
                            }
                        }
                    }

                    is Resource.Error -> sendEvent(ProjectDetailsReducer.Event.Error.IssueMoveToSprintError)
                    is Resource.Loading -> Unit
                }
            }
        }
    }
}