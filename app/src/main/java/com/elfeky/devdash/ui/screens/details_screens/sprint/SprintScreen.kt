package com.elfeky.devdash.ui.screens.details_screens.sprint

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.elfeky.devdash.ui.common.dialogs.delete.DeleteConfirmationDialog
import com.elfeky.devdash.ui.common.dialogs.issue.IssueDialog
import com.elfeky.devdash.ui.common.dialogs.sprint.SprintDialog
import com.elfeky.devdash.ui.screens.details_screens.sprint.issue_comment.CommentsSheetContent
import com.elfeky.devdash.ui.utils.rememberFlowWithLifecycle
import com.elfeky.devdash.ui.utils.toStringDate
import com.elfeky.domain.model.issue.IssueFormFields
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SprintScreen(
    role: String,
    viewModel: SprintViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onIssueClick: (issueId: Int) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val issues = state.issues.collectAsLazyPagingItems()
    val comments = state.comments.collectAsLazyPagingItems()

    val uiEffectFlow = rememberFlowWithLifecycle(viewModel.uiEffect)

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showCommentsSheet by remember { mutableStateOf(false) }

    LaunchedEffect(uiEffectFlow) {
        uiEffectFlow.collect { effect ->
            when (effect) {
                is SprintReducer.Effect.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            effect.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                SprintReducer.Effect.NavigateBack -> onBackClick()
                is SprintReducer.Effect.NavigateToIssueDetails -> onIssueClick(effect.issueId)

                is SprintReducer.Effect.Reload.Issues -> issues.refresh()
                is SprintReducer.Effect.Reload.Comments -> {
                    Log.d("SprintScreen", "refresh comments")
                    comments.refresh()
                    viewModel.onEvent(SprintReducer.Event.UpdateState(tempComment = null))
                }

                is SprintReducer.Effect.ShowCommentBottomSheet -> {
                    viewModel.onEvent(SprintReducer.Event.UpdateState(issueCommentId = effect.issueId))
                    viewModel.onEvent(SprintReducer.Event.IssueCommentLoad(effect.issueId))
                    showCommentsSheet = true
                }

                else -> Unit
            }
        }
    }

    SprintContent(
        role = role,
        uiState = state,
        issues = issues,
        onEvent = viewModel::onEvent,
        snackbarHostState = snackbarHostState,
        onNavigateBack = { viewModel.onEvent(SprintReducer.Event.BackClicked) }
    )

    state.dialog?.let { dialogType ->
        val onDismiss = { viewModel.onEvent(SprintReducer.Event.DismissDialog) }
        when (dialogType) {
            is SprintReducer.State.Dialog.DeleteIssueConfirmation -> {
                DeleteConfirmationDialog(
                    title = "Delete Issue",
                    text = "Are you sure you want to delete this issue?",
                    onDismiss = onDismiss,
                    onConfirm = {
                        viewModel.onEvent(
                            SprintReducer.Event.ConfirmDeleteIssueClicked(
                                dialogType.issueId
                            )
                        )
                    }
                )
            }

            SprintReducer.State.Dialog.DeleteSprintConfirmation -> {
                DeleteConfirmationDialog(
                    title = "Delete Sprint",
                    text = "Are you sure you want to delete this sprint?",
                    onDismiss = onDismiss,
                    onConfirm = { viewModel.onEvent(SprintReducer.Event.ConfirmDeleteSprintClicked) }
                )
            }

            is SprintReducer.State.Dialog.EditIssue -> {
                IssueDialog(
                    issue = dialogType.issue,
                    assigneeList = state.users,
                    onDismiss = onDismiss,
                    onSubmit = {
                        val updatedIssue = dialogType.issue.copy(
                            title = it.title,
                            description = it.description,
                            priority = it.priority.text,
                            labels = it.labels.toString(),
                            type = it.type.text,
                            status = it.status.text,
                            startDate = it.startDate?.toStringDate() ?: "",
                            deadline = it.deadline?.toStringDate() ?: ""
                        )
                        viewModel.onEvent(
                            SprintReducer.Event.ConfirmEditIssueClicked(
                                updatedIssue,
                                it.assignedUsers
                            )
                        )
                    },
                    isBacklog = false
                )
            }

            is SprintReducer.State.Dialog.EditSprint -> {
                SprintDialog(
                    sprint = dialogType.sprint,
                    onDismiss = onDismiss,
                    onSubmit = {
                        val updatedSprint = dialogType.sprint.copy(
                            title = it.title, status = it.status, startDate = it.startDate,
                            endDate = it.endDate, description = it.description, summary = it.summary
                        )
                        viewModel.onEvent(SprintReducer.Event.ConfirmEditSprintClicked(updatedSprint))
                    }
                )
            }

            SprintReducer.State.Dialog.CreateIssue -> {
                IssueDialog(
                    assigneeList = state.users,
                    onDismiss = onDismiss,
                    onSubmit = {
                        val newIssue = IssueFormFields(
                            title = it.title,
                            description = it.description,
                            priority = it.priority.text,
                            labels = it.labels.toString(),
                            type = it.type.text,
                            status = it.status.text,
                            startDate = it.startDate?.toStringDate() ?: "",
                            deadline = it.deadline?.toStringDate() ?: "",
                            isBacklog = false,
                            deliveredDate = "",
                            lastUpdate = LocalDateTime.now().toString()
                        )
                        viewModel.onEvent(SprintReducer.Event.ConfirmCreateIssueClicked(newIssue))
                    },
                    isBacklog = false
                )
            }
        }
    }

    if (showCommentsSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(SprintReducer.Event.UpdateState(issueCommentId = null))
                showCommentsSheet = false
            },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            CommentsSheetContent(
                comments = comments,
                tempComment = state.tempComment,
                user = state.userProfile,
                onSendClick = { viewModel.onEvent(SprintReducer.Event.SendCommentClicked(it)) },
                onEditClick = {},
                onDeleteClick = {}
            )
        }
    }
}