package com.elfeky.devdash.ui.screens.details_screens.sprint

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.elfeky.devdash.ui.base.BaseViewModel
import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.sprint.SprintRequest
import com.elfeky.domain.usecase.account.GetUserProfileUseCase
import com.elfeky.domain.usecase.assign.AssignUserIssueUseCase
import com.elfeky.domain.usecase.assign.UnassignUserIssueUseCase
import com.elfeky.domain.usecase.comment.GetCommentsUseCase
import com.elfeky.domain.usecase.comment.SendCommentUseCase
import com.elfeky.domain.usecase.issue.DeleteIssueUseCase
import com.elfeky.domain.usecase.issue.UpdateIssueUseCase
import com.elfeky.domain.usecase.pin.get.GetPinnedIssuesUseCase
import com.elfeky.domain.usecase.pin.get.GetPinnedSprintsUseCase
import com.elfeky.domain.usecase.pin.pin.PinIssueUseCase
import com.elfeky.domain.usecase.pin.pin.PinSprintUseCase
import com.elfeky.domain.usecase.pin.unpin.UnpinIssueUseCase
import com.elfeky.domain.usecase.pin.unpin.UnpinSprintUseCase
import com.elfeky.domain.usecase.project.GetProjectByIdUseCase
import com.elfeky.domain.usecase.sprint.DeleteSprintUseCase
import com.elfeky.domain.usecase.sprint.GetSprintByIdUseCase
import com.elfeky.domain.usecase.sprint.UpdateSprintUseCase
import com.elfeky.domain.usecase.sprint_issue.CreateSprintIssueUseCase
import com.elfeky.domain.usecase.sprint_issue.GetSprintIssuesUseCase
import com.elfeky.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SprintViewModel.Factory::class)
class SprintViewModel @AssistedInject constructor(
    @Assisted private val sprintId: Int,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getSprintByIdUseCase: GetSprintByIdUseCase,
    private val getSprintIssuesUseCase: GetSprintIssuesUseCase,
    private val getProjectByIdUseCase: GetProjectByIdUseCase,
    private val getPinnedSprintsUseCase: GetPinnedSprintsUseCase,
    private val getPinnedIssuesUseCase: GetPinnedIssuesUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val sendCommentUseCase: SendCommentUseCase,
    private val updateSprintUseCase: UpdateSprintUseCase,
    private val updateIssueUseCase: UpdateIssueUseCase,
    private val pinIssueUseCase: PinIssueUseCase,
    private val unpinIssueUseCase: UnpinIssueUseCase,
    private val pinSprintUseCase: PinSprintUseCase,
    private val unpinSprintUseCase: UnpinSprintUseCase,
    private val deleteSprintUseCase: DeleteSprintUseCase,
    private val deleteIssueUseCase: DeleteIssueUseCase,
    private val createSprintIssueUseCase: CreateSprintIssueUseCase,
    private val assignUserIssueUseCase: AssignUserIssueUseCase,
    private val unassignUserIssueUseCase: UnassignUserIssueUseCase
) : BaseViewModel<SprintReducer.State, SprintReducer.Event, SprintReducer.Effect>(
    initialState = SprintReducer.State(),
    reducer = SprintReducer()
) {

    @AssistedFactory
    interface Factory {
        fun create(sprintId: Int): SprintViewModel
    }

    init {
        observeEffects()
        loadAllSprintData()
    }

    fun onEvent(event: SprintReducer.Event) {
        when (event) {
            is SprintReducer.Event.UpdateState,
            SprintReducer.Event.DismissDialog,
            SprintReducer.Event.DeleteSprintClicked,
            SprintReducer.Event.EditSprintClicked,
            SprintReducer.Event.CreateIssueClicked,
            is SprintReducer.Event.DeleteIssueClicked,
            is SprintReducer.Event.EditIssueClicked -> sendEvent(event)

            else -> sendEventForEffect(event)
        }

    }

    private fun observeEffects() {
        viewModelScope.launch {
            internalEffect.collect { effect ->
                when (effect) {
                    is SprintReducer.Effect.NavigateBack,
                    is SprintReducer.Effect.NavigateToIssueDetails,
                    is SprintReducer.Effect.ShowCommentBottomSheet,
                    is SprintReducer.Effect.ShowSnackbar -> sendUiEffect(effect)

                    is SprintReducer.Effect.Reload -> handleReloadEffect(effect)
                    is SprintReducer.Effect.Trigger -> handleTriggerEffect(effect)
                }
            }
        }
    }

    private fun handleReloadEffect(effect: SprintReducer.Effect.Reload) {
        when (effect) {
            SprintReducer.Effect.Reload.SprintInfo -> loadSprint()
            SprintReducer.Effect.Reload.PinnedIssues -> loadPinnedIssues()
            SprintReducer.Effect.Reload.Issues,
            SprintReducer.Effect.Reload.Comments -> sendUiEffect(effect)
        }
    }

    private fun handleTriggerEffect(effect: SprintReducer.Effect.Trigger) {
        when (effect) {
            is SprintReducer.Effect.Trigger.DeleteSprint -> deleteSprint()
            is SprintReducer.Effect.Trigger.UpdateSprint -> updateSprint(effect.sprint)
            is SprintReducer.Effect.Trigger.ToggleSprintPin -> toggleSprintPin()
            is SprintReducer.Effect.Trigger.CreateIssue -> addIssue(effect.issue)
            is SprintReducer.Effect.Trigger.DeleteIssue -> deleteIssue(effect.issueId)
            is SprintReducer.Effect.Trigger.UpdateIssue -> updateIssue(
                effect.issue,
                effect.assignedUsers
            )

            is SprintReducer.Effect.Trigger.IssueComment -> loadComments(effect.issueId)
            is SprintReducer.Effect.Trigger.ToggleIssuePin -> toggleIssuePin(effect.issueId)
            is SprintReducer.Effect.Trigger.MoveIssue -> moveIssue(effect.status)
            is SprintReducer.Effect.Trigger.SendComment -> sendComment(effect.text)
        }
    }

    private fun loadAllSprintData() {
        viewModelScope.launch {
            onEvent(SprintReducer.Event.UpdateState(isLoading = true))
            coroutineScope {
                launch { loadSprint() }
                launch { loadPinnedStatus() }
                launch { loadIssues() }
                launch { loadUserProfile() }
                launch { loadPinnedIssues() }
            }
            onEvent(SprintReducer.Event.UpdateState(isLoading = false))
        }
    }

    private suspend fun <T> executeResourceFlow(
        flow: Flow<Resource<T>>,
        onSuccess: suspend (T?) -> Unit,
        onError: (String) -> SprintReducer.Event.OperationError
    ) {
        flow.collect { result ->
            when (result) {
                is Resource.Success -> onSuccess(result.data)
                is Resource.Error -> onEvent(
                    onError(
                        result.message ?: "An unknown error occurred"
                    )
                )

                is Resource.Loading -> Unit
            }
        }
    }

    private fun loadUserProfile() = viewModelScope.launch {
        executeResourceFlow(
            getUserProfileUseCase(),
            onSuccess = { profile -> onEvent(SprintReducer.Event.UpdateState(userProfile = profile)) },
            onError = { SprintReducer.Event.OperationError("Error loading user profile: $it") }
        )
    }

    private fun loadSprint() = viewModelScope.launch {
        executeResourceFlow(
            getSprintByIdUseCase(sprintId),
            onSuccess = { sprint ->
                onEvent(SprintReducer.Event.UpdateState(sprint = sprint))
                sprint?.projectId?.let { loadUsers(it) }
            },
            onError = { SprintReducer.Event.OperationError("Error loading sprint: $it") }
        )
    }

    private fun loadPinnedStatus() = viewModelScope.launch {
        executeResourceFlow(
            getPinnedSprintsUseCase(),
            onSuccess = { sprints -> onEvent(SprintReducer.Event.UpdateState(isPinned = sprints!!.any { it.id == sprintId })) },
            onError = { SprintReducer.Event.OperationError("Failed to check if sprint is pinned: $it") }
        )
    }

    private fun loadIssues() {
        onEvent(
            SprintReducer.Event.UpdateState(
                issues = getSprintIssuesUseCase(sprintId).cachedIn(
                    viewModelScope
                )
            )
        )
    }

    private fun loadPinnedIssues() = viewModelScope.launch {
        executeResourceFlow(
            getPinnedIssuesUseCase(),
            onSuccess = { onEvent(SprintReducer.Event.UpdateState(pinnedIssues = it!!)) },
            onError = { SprintReducer.Event.OperationError("Failed to load pinned issues: $it") }
        )
    }

    private suspend fun loadUsers(projectId: Int) {
        executeResourceFlow(
            getProjectByIdUseCase(projectId),
            onSuccess = { onEvent(SprintReducer.Event.UpdateState(users = it!!.tenant.joinedUsers)) },
            onError = { SprintReducer.Event.OperationError("Failed to load users: $it") }
        )
    }

    private fun deleteSprint() = viewModelScope.launch {
        executeResourceFlow(
            deleteSprintUseCase(sprintId),
            onSuccess = { onEvent(SprintReducer.Event.AsyncOperationCompleted.SprintDelete) },
            onError = { SprintReducer.Event.OperationError("Failed to delete sprint: $it") }
        )
    }

    private fun updateSprint(sprint: Sprint) = viewModelScope.launch {
        val request = SprintRequest(
            sprint.title, sprint.description, sprint.startDate,
            sprint.endDate, sprint.status, sprint.summary
        )
        executeResourceFlow(
            updateSprintUseCase(sprintId, request),
            onSuccess = { onEvent(SprintReducer.Event.AsyncOperationCompleted.SprintUpdate) },
            onError = { SprintReducer.Event.OperationError("Failed to update sprint: $it") }
        )
    }

    private fun toggleSprintPin() = viewModelScope.launch {
        val isCurrentlyPinned = state.value.isPinned
        val operation =
            if (isCurrentlyPinned) unpinSprintUseCase(sprintId) else pinSprintUseCase(sprintId)
        executeResourceFlow(
            operation,
            onSuccess = {
                onEvent(SprintReducer.Event.UpdateState(isPinned = !isCurrentlyPinned))
                sendUiEffect(SprintReducer.Effect.ShowSnackbar(if (!isCurrentlyPinned) "Sprint pinned" else "Sprint unpinned"))
            },
            onError = { SprintReducer.Event.OperationError("Failed to pin/unpin sprint: $it") }
        )
    }

    private fun addIssue(issue: IssueFormFields) = viewModelScope.launch {
        executeResourceFlow(
            createSprintIssueUseCase(sprintId, issue),
            onSuccess = { onEvent(SprintReducer.Event.AsyncOperationCompleted.IssueCreate) },
            onError = { SprintReducer.Event.OperationError("Failed to create new issue: $it") }
        )
    }

    private fun deleteIssue(issueId: Int) = viewModelScope.launch {
        executeResourceFlow(
            deleteIssueUseCase(issueId),
            onSuccess = { onEvent(SprintReducer.Event.AsyncOperationCompleted.IssueDelete) },
            onError = { SprintReducer.Event.OperationError("Failed to delete issue: $it") }
        )
    }

    private fun updateIssue(issue: Issue, assignedUsers: List<UserProfile>) =
        viewModelScope.launch {
            val currentAssigned = issue.assignedUsers.toSet()
            val newAssigned = assignedUsers.toSet()

            (currentAssigned - newAssigned).forEach { user ->
                unassignUserIssueUseCase(issue.id, user.id).collect {}
            }
            (newAssigned - currentAssigned).forEach { user ->
                assignUserIssueUseCase(issue.id, user.id).collect {}
            }

            executeResourceFlow(
                updateIssueUseCase(issue, sprintId),
                onSuccess = { onEvent(SprintReducer.Event.AsyncOperationCompleted.IssueUpdate) },
                onError = { SprintReducer.Event.OperationError("Failed to update issue: $it") }
            )
        }

    private fun toggleIssuePin(issueId: Int) = viewModelScope.launch {
        val isPinned = state.value.pinnedIssues.any { it.id == issueId }
        val operation = if (isPinned) unpinIssueUseCase(issueId) else pinIssueUseCase(issueId)
        executeResourceFlow(
            operation,
            onSuccess = {
                sendInternalEffect(SprintReducer.Effect.Reload.PinnedIssues)
                sendUiEffect(SprintReducer.Effect.ShowSnackbar(if (!isPinned) "Issue pinned" else "Issue unpinned"))
            },
            onError = { SprintReducer.Event.OperationError("Failed to pin/unpin issue: $it") }
        )
    }

    private fun moveIssue(status: String) = viewModelScope.launch {
        Log.d("SprintViewModel", "Dragged Issue: " + state.value.draggedIssue.toString())
        state.value.draggedIssue?.let {
            executeResourceFlow(
                updateIssueUseCase(it.copy(status = status), sprintId),
                onSuccess = { onEvent(SprintReducer.Event.AsyncOperationCompleted.IssueMove) },
                onError = { SprintReducer.Event.OperationError("Failed to move issue: $it") }
            )
        }
        onEvent(SprintReducer.Event.UpdateState(draggedIssue = null))
    }

    private fun loadComments(issueId: Int) = viewModelScope.launch {
        onEvent(
            SprintReducer.Event.UpdateState(
                comments = getCommentsUseCase(issueId).cachedIn(
                    viewModelScope
                )
            )
        )
    }

    private fun sendComment(text: String) = viewModelScope.launch {
        executeResourceFlow(
            sendCommentUseCase(state.value.issueCommentId!!, text),
            onSuccess = { onEvent(SprintReducer.Event.AsyncOperationCompleted.CommentSend) },
            onError = { SprintReducer.Event.OperationError("Failed to send comment: $it") }
        )
    }
}