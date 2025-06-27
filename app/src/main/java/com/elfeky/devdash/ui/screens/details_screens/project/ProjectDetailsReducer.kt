package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.elfeky.devdash.ui.base.Reducer
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.sprint.SprintRequest
import kotlinx.coroutines.flow.Flow

class ProjectDetailsReducer :
    Reducer<ProjectDetailsReducer.State, ProjectDetailsReducer.Event, ProjectDetailsReducer.Effect> {

    @Immutable
    sealed class Event : Reducer.ViewEvent {

        sealed class Update : Event() {
            data class IsLoading(val isLoading: Boolean) : Update()
            data class ProjectInfo(val project: Project) : Update()
            data class PinnedStatus(val isPinned: Boolean) : Update()
            data class SprintsFlow(val sprintsFlow: Flow<PagingData<Sprint>>) : Update()
            data class PinnedIssues(val pinnedIssues: List<Issue>) : Update()
            data class PinnedSprints(val pinnedSprints: List<Sprint>) : Update()
            data class BacklogIssuesFlow(val issuesFlow: Flow<PagingData<Issue>>) : Update()
        }

        data object BackClicked : Event()
        data class CopyTextClicked(val text: String) : Event()
        data object DismissDialogClicked : Event()
        data class RemoveMemberClicked(val memberId: Int) : Event()
        data object LoadBacklogIssues : Event()
        data object LoadMoreBacklogIssuesClicked : Event()

        sealed class ProjectAction : Event() {
            data object PinClicked : ProjectAction()
            data object DeleteClicked : ProjectAction()
            data object ConfirmDeleteClicked : ProjectAction()
            data object EditClicked : ProjectAction()
            data class ConfirmEditClicked(val projectRequest: ProjectRequest) : ProjectAction()
            data object DeletionCompleted : ProjectAction()
            data object UpdateCompleted : ProjectAction()
            data class PinCompleted(val isPinned: Boolean) : ProjectAction()
        }

        sealed class SprintAction : Event() {
            data object CreateClicked : SprintAction()
            data class ConfirmCreateClicked(val sprint: SprintRequest) : SprintAction()
            data class Clicked(val sprintId: Int) : SprintAction()
            data class SwipedToDelete(val sprintId: Int) : SprintAction()
            data class ConfirmDeleteClicked(val sprintId: Int) : SprintAction()
            data class SwipedToPin(val sprintId: Int) : SprintAction()
            data object Added : SprintAction()
            data object DeletionCompleted : SprintAction()
            data class PinCompleted(val isPinned: Boolean) : ProjectAction()
        }

        sealed class IssueAction : Event() {
            data object CreateClicked : IssueAction()
            data class ConfirmCreateClicked(val issueBacklog: IssueFormFields) : IssueAction()
            data class Clicked(val issue: Issue) : IssueAction()
            data class SwipedToDelete(val issueId: Int) : IssueAction()
            data class ConfirmDeleteClicked(val issueId: Int) : IssueAction()
            data class SwipedToPin(val issueId: Int) : IssueAction()
            data object Added : IssueAction()
            data object DeletionCompleted : IssueAction()
            data class PinCompleted(val isPinned: Boolean) : IssueAction()
            data class IssueDroppedOnSprint(val issueId: Int) : IssueAction()
        }

        sealed class Error(val message: String) : Event() {
            data object ProjectLoadError : Error("Error loading project details")
            data object PinnedProjectLoadError : Error("Failed to check if project is pinned")
            data object SprintsLoadError : Error("Failed to load sprints")
            data object PinnedSprintsLoadError : Error("Failed to load pinned sprints")
            data object PinnedIssuesLoadError : Error("Failed to load pinned Issues")
            data object ProjectDeleteError : Error("Failed to delete project")
            data object ProjectUpdateError : Error("Failed to update project")
            data object ProjectPinError : Error("Failed to pin/unpin project")
            data object SprintAddError : Error("Failed to add sprint")
            data object IssueAddError : Error("Failed to add issue")
            data object SprintDeleteError : Error("Failed to delete sprint")
            data object IssueDeleteError : Error("Failed to delete issue")
            data object SprintPinError : Error("Failed to pin/unpin sprint")
            data object IssuePinError : Error("Failed to pin/unpin issue")
            data object BacklogIssuesLoadError : Error("Failed to load backlog issues")
            data object LoadMoreBacklogIssuesError : Error("Failed to load more backlog issues")
        }
    }

    @Immutable
    sealed class Effect : Reducer.ViewEffect {
        data object NavigateBack : Effect()
        data class NavigateToSprintDetails(val sprintId: Int) : Effect()
        data class ShowSnackbar(val message: String, val isLong: Boolean = false) : Effect()
        data class ShowDialog(val type: DialogType) : Effect()
        data object DismissDialog : Effect()
        data class AddSprintIssue(val issueId: Int) : Effect()
        data object TriggerReloadAllData : Effect()
        data object TriggerLoadSprints : Effect()
        data object TriggerDeleteProject : Effect()
        data class TriggerUpdateProject(val projectRequest: ProjectRequest) : Effect()
        data object TriggerToggleProjectPin : Effect()
        data class TriggerAddSprint(val sprint: SprintRequest) : Effect()
        data class TriggerAddIssue(val issueBacklog: IssueFormFields) : Effect()
        data class TriggerDeleteSprint(val sprintId: Int) : Effect()
        data class TriggerDeleteIssue(val issueId: Int) : Effect()
        data class TriggerToggleSprintPin(val sprintId: Int) : Effect()
        data class TriggerToggleIssuePin(val issueId: Int) : Effect()
        data object TriggerLoadBacklogIssues : Effect()
    }

    @Immutable
    data class State(
        val project: Project? = null,
        val isPinned: Boolean = false,
        val isLoading: Boolean = false,
        val sprintsFlow: Flow<PagingData<Sprint>>? = null,
        val pinnedIssues: List<Issue> = emptyList(),
        val pinnedSprints: List<Sprint> = emptyList(),
        val backlogIssuesFlow: Flow<PagingData<Issue>>? = null,
    ) : Reducer.ViewState

    @Immutable
    sealed class DialogType {
        data object EditProject : DialogType()
        data class EditIssue(val issue: Issue) : DialogType()
        data object CreateSprint : DialogType()
        data object CreateIssue : DialogType()
        data object DeleteProjectConfirmation : DialogType()
        data class DeleteSprintConfirmation(val sprintId: Int) : DialogType()
        data class DeleteIssueConfirmation(val issueId: Int) : DialogType()
    }

    override fun reduce(
        previousState: State,
        event: Event
    ): Pair<State, Effect?> {
        return when (event) {
            is Event.Update.IsLoading -> previousState.copy(isLoading = event.isLoading) to null
            is Event.Update.ProjectInfo -> previousState.copy(project = event.project) to null
            is Event.Update.PinnedStatus -> previousState.copy(isPinned = event.isPinned) to null
            is Event.Update.SprintsFlow -> previousState.copy(sprintsFlow = event.sprintsFlow) to null
            is Event.Update.PinnedIssues -> previousState.copy(pinnedIssues = event.pinnedIssues) to null
            is Event.Update.PinnedSprints -> previousState.copy(pinnedSprints = event.pinnedSprints) to null

            is Event.Update.BacklogIssuesFlow -> previousState.copy(backlogIssuesFlow = event.issuesFlow) to null

            Event.BackClicked -> previousState to Effect.NavigateBack
            is Event.CopyTextClicked -> previousState to Effect.ShowSnackbar(event.text)
            Event.DismissDialogClicked -> previousState to Effect.DismissDialog
            is Event.RemoveMemberClicked -> previousState to Effect.ShowSnackbar("Remove member ${event.memberId} (not implemented yet)")

            Event.LoadBacklogIssues -> previousState to Effect.TriggerLoadBacklogIssues
            Event.LoadMoreBacklogIssuesClicked -> previousState to Effect.ShowSnackbar("Paging handles loading more backlog issues automatically.")

            Event.ProjectAction.PinClicked -> previousState to Effect.TriggerToggleProjectPin
            Event.ProjectAction.DeleteClicked -> previousState to Effect.ShowDialog(DialogType.DeleteProjectConfirmation)
            Event.ProjectAction.ConfirmDeleteClicked -> previousState to Effect.TriggerDeleteProject
            Event.ProjectAction.EditClicked -> previousState to Effect.ShowDialog(DialogType.EditProject)
            is Event.ProjectAction.ConfirmEditClicked -> previousState to Effect.TriggerUpdateProject(
                event.projectRequest
            )

            Event.ProjectAction.DeletionCompleted -> previousState to Effect.NavigateBack
            Event.ProjectAction.UpdateCompleted -> previousState to Effect.TriggerReloadAllData
            is Event.ProjectAction.PinCompleted -> previousState to Effect.ShowSnackbar(if (event.isPinned) "Project Pinned Successfully" else "Project Unpinned Successfully")

            Event.SprintAction.CreateClicked -> previousState to Effect.ShowDialog(DialogType.CreateSprint)
            is Event.SprintAction.ConfirmCreateClicked -> previousState to Effect.TriggerAddSprint(
                event.sprint
            )

            is Event.SprintAction.Clicked -> previousState to Effect.NavigateToSprintDetails(event.sprintId)
            is Event.SprintAction.SwipedToDelete -> previousState to Effect.ShowDialog(
                DialogType.DeleteSprintConfirmation(
                    event.sprintId
                )
            )

            is Event.SprintAction.ConfirmDeleteClicked -> previousState to Effect.TriggerDeleteSprint(
                event.sprintId
            )

            is Event.SprintAction.SwipedToPin -> previousState to Effect.TriggerToggleSprintPin(
                event.sprintId
            )

            is Event.SprintAction.Added -> {
                previousState to Effect.TriggerLoadSprints
            }

            Event.SprintAction.DeletionCompleted -> previousState to Effect.TriggerReloadAllData
            is Event.SprintAction.PinCompleted -> previousState to Effect.ShowSnackbar(if (event.isPinned) "Sprint Pinned Successfully" else "Sprint Unpinned Successfully")


            Event.IssueAction.CreateClicked -> previousState to Effect.ShowDialog(DialogType.CreateIssue)
            is Event.IssueAction.ConfirmCreateClicked -> previousState to Effect.TriggerAddIssue(
                event.issueBacklog
            )

            is Event.IssueAction.Clicked -> previousState to Effect.ShowDialog(
                DialogType.EditIssue(
                    event.issue
                )
            )

            is Event.IssueAction.SwipedToDelete -> previousState to Effect.ShowDialog(
                DialogType.DeleteIssueConfirmation(
                    event.issueId
                )
            )

            is Event.IssueAction.ConfirmDeleteClicked -> previousState to Effect.TriggerDeleteIssue(
                event.issueId
            )

            is Event.IssueAction.SwipedToPin -> previousState to Effect.TriggerToggleIssuePin(event.issueId)
            Event.IssueAction.Added -> {
                previousState to Effect.TriggerLoadBacklogIssues
            }

            Event.IssueAction.DeletionCompleted -> previousState to Effect.TriggerLoadBacklogIssues
            is Event.IssueAction.PinCompleted -> previousState to Effect.ShowSnackbar(if (event.isPinned) "Issue Pinned Successfully" else "Issue Unpinned Successfully")
            is Event.IssueAction.IssueDroppedOnSprint -> previousState to Effect.AddSprintIssue(
                event.issueId
            )

            is Event.Error -> previousState to Effect.ShowSnackbar(event.message)
        }
    }

    companion object {
        val initialState = State()
    }
}