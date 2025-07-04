package com.elfeky.devdash.ui.screens.details_screens.sprint

import androidx.paging.PagingData
import com.elfeky.devdash.ui.base.Reducer
import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.comment.Comment
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.model.sprint.Sprint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.annotation.concurrent.Immutable

class SprintReducer : Reducer<SprintReducer.State, SprintReducer.Event, SprintReducer.Effect> {

    @Immutable
    data class State(
        val sprint: Sprint? = null,
        val isLoading: Boolean = false,
        val isPinned: Boolean = false,
        val issues: Flow<PagingData<Issue>> = flowOf(PagingData.empty()),
        val pinnedIssues: List<Issue> = emptyList(),
        val users: List<UserProfile> = emptyList(),
        val comments: Flow<PagingData<Comment>> = flowOf(PagingData.empty()),
        val draggedIssue: Issue? = null,
        val dialog: Dialog? = null
    ) : Reducer.ViewState {
        sealed class Dialog {
            data class EditSprint(val sprint: Sprint) : Dialog()
            data object CreateIssue : Dialog()
            data class EditIssue(val issue: Issue) : Dialog()
            data object DeleteSprintConfirmation : Dialog()
            data class DeleteIssueConfirmation(val issueId: Int) : Dialog()
        }
    }

    @Immutable
    sealed class Event : Reducer.ViewEvent {
        data class UpdateState(
            val isLoading: Boolean? = null,
            val sprint: Sprint? = null,
            val isPinned: Boolean? = null,
            val issues: Flow<PagingData<Issue>>? = null,
            val pinnedIssues: List<Issue>? = null,
            val users: List<UserProfile>? = null,
            val comments: Flow<PagingData<Comment>>? = null,
            val draggedIssue: Issue? = null
        ) : Event()

        data class OperationError(val message: String) : Event()
        data object BackClicked : Event()
        data object DismissDialog : Event()
        data object DeleteSprintClicked : Event()
        data object ConfirmDeleteSprintClicked : Event()
        data object EditSprintClicked : Event()
        data class ConfirmEditSprintClicked(val sprint: Sprint) : Event()
        data object PinSprintClicked : Event()
        data class IssueClicked(val issueId: Int) : Event()
        data object CreateIssueClicked : Event()
        data class ConfirmCreateIssueClicked(val issue: IssueFormFields) : Event()
        data class DeleteIssueClicked(val issueId: Int) : Event()
        data class ConfirmDeleteIssueClicked(val issueId: Int) : Event()
        data class PinIssueClicked(val issueId: Int) : Event()
        data class EditIssueClicked(val issue: Issue) : Event()
        data class ConfirmEditIssueClicked(val issue: Issue, val assignedUsers: List<UserProfile>) :
            Event()

        data class IssueDropped(val status: String) : Event()
        data class SendCommentClicked(val text: String) : Event()

        sealed class AsyncOperationCompleted : Event() {
            data object SprintDelete : AsyncOperationCompleted()
            data object SprintUpdate : AsyncOperationCompleted()
            data object IssueCreate : AsyncOperationCompleted()
            data object IssueDelete : AsyncOperationCompleted()
            data object IssueUpdate : AsyncOperationCompleted()
            data object IssueMove : AsyncOperationCompleted()
            data object CommentSend : AsyncOperationCompleted()
        }
    }

    @Immutable
    sealed class Effect : Reducer.ViewEffect {
        data object NavigateBack : Effect()
        data class NavigateToIssueDetails(val issueId: Int) : Effect()
        data class ShowSnackbar(val message: String) : Effect()

        sealed class Reload : Effect() {
            data object SprintInfo : Reload()
            data object Issues : Reload()
            data object PinnedIssues : Reload()
            data object Comments : Reload()
        }

        sealed class Trigger : Effect() {
            data object DeleteSprint : Trigger()
            data class UpdateSprint(val sprint: Sprint) : Trigger()
            data object ToggleSprintPin : Trigger()
            data class CreateIssue(val issue: IssueFormFields) : Trigger()
            data class DeleteIssue(val issueId: Int) : Trigger()
            data class UpdateIssue(val issue: Issue, val assignedUsers: List<UserProfile>) :
                Trigger()

            data class ToggleIssuePin(val issueId: Int) : Trigger()
            data class MoveIssue(val status: String) : Trigger()
            data class SendComment(val text: String) : Trigger()
        }
    }

    override fun reduce(previousState: State, event: Event): Pair<State, Effect?> {
        return when (event) {
            is Event.UpdateState -> previousState.copy(
                isLoading = event.isLoading ?: previousState.isLoading,
                sprint = event.sprint ?: previousState.sprint,
                isPinned = event.isPinned ?: previousState.isPinned,
                issues = event.issues ?: previousState.issues,
                pinnedIssues = event.pinnedIssues ?: previousState.pinnedIssues,
                users = event.users ?: previousState.users,
                comments = event.comments ?: previousState.comments,
                draggedIssue = event.draggedIssue ?: previousState.draggedIssue
            ) to null

            is Event.OperationError -> previousState.copy(isLoading = false) to Effect.ShowSnackbar(
                event.message
            )

            Event.BackClicked -> previousState to Effect.NavigateBack
            Event.DismissDialog -> previousState.copy(dialog = null) to null
            Event.PinSprintClicked -> previousState to Effect.Trigger.ToggleSprintPin
            Event.DeleteSprintClicked -> previousState.copy(dialog = State.Dialog.DeleteSprintConfirmation) to null
            Event.ConfirmDeleteSprintClicked -> previousState.copy(
                isLoading = true,
                dialog = null
            ) to Effect.Trigger.DeleteSprint

            Event.EditSprintClicked -> previousState.sprint?.let {
                previousState.copy(dialog = State.Dialog.EditSprint(it)) to null
            } ?: (previousState to Effect.ShowSnackbar("Sprint data not available to edit"))

            is Event.ConfirmEditSprintClicked -> previousState.copy(
                isLoading = true,
                dialog = null
            ) to Effect.Trigger.UpdateSprint(event.sprint)

            is Event.IssueClicked -> previousState to Effect.NavigateToIssueDetails(event.issueId)
            Event.CreateIssueClicked -> previousState.copy(dialog = State.Dialog.CreateIssue) to null
            is Event.ConfirmCreateIssueClicked -> previousState.copy(dialog = null) to Effect.Trigger.CreateIssue(
                event.issue
            )

            is Event.DeleteIssueClicked -> previousState.copy(
                dialog = State.Dialog.DeleteIssueConfirmation(
                    event.issueId
                )
            ) to null

            is Event.ConfirmDeleteIssueClicked -> previousState.copy(dialog = null) to Effect.Trigger.DeleteIssue(
                event.issueId
            )

            is Event.EditIssueClicked -> previousState.copy(dialog = State.Dialog.EditIssue(event.issue)) to null
            is Event.ConfirmEditIssueClicked -> previousState.copy(dialog = null) to Effect.Trigger.UpdateIssue(
                event.issue,
                event.assignedUsers
            )

            is Event.PinIssueClicked -> previousState to Effect.Trigger.ToggleIssuePin(event.issueId)
            is Event.IssueDropped -> previousState to Effect.Trigger.MoveIssue(event.status)
            is Event.SendCommentClicked -> previousState to Effect.Trigger.SendComment(event.text)

            Event.AsyncOperationCompleted.SprintDelete -> previousState.copy(isLoading = false) to Effect.NavigateBack
            Event.AsyncOperationCompleted.SprintUpdate -> previousState.copy(isLoading = false) to Effect.Reload.SprintInfo
            Event.AsyncOperationCompleted.IssueCreate -> previousState to Effect.Reload.Issues
            Event.AsyncOperationCompleted.IssueDelete -> previousState to Effect.Reload.Issues
            Event.AsyncOperationCompleted.IssueUpdate -> previousState to Effect.Reload.Issues
            Event.AsyncOperationCompleted.IssueMove -> previousState to Effect.Reload.Issues
            Event.AsyncOperationCompleted.CommentSend -> previousState to Effect.Reload.Comments
        }
    }
}