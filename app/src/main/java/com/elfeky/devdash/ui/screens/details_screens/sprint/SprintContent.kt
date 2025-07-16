package com.elfeky.devdash.ui.screens.details_screens.sprint

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.elfeky.devdash.ui.common.commentList
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.common.sprintList
import com.elfeky.devdash.ui.screens.details_screens.components.ScreenContainer
import com.elfeky.devdash.ui.screens.details_screens.sprint.components.KanbanBoard
import com.elfeky.devdash.ui.screens.details_screens.sprint.components.comment.CommentsSheetContent
import com.elfeky.devdash.ui.screens.details_screens.sprint.components.comment.CommentsViewModel
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.comment.Comment
import com.elfeky.domain.model.issue.Issue
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SprintContent(
    role: String,
    uiState: SprintReducer.State,
    issues: LazyPagingItems<Issue>,
    comments: LazyPagingItems<Comment>,
    onEvent: (SprintReducer.Event) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit,
    commentsViewModel: CommentsViewModel = viewModel(),
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showCommentsSheet by remember { mutableStateOf(false) }

    val commentsUiState by commentsViewModel.uiState.collectAsState()

    ScreenContainer(
        title = uiState.sprint?.title ?: "",
        isPinned = uiState.isPinned,
        isOwner = role.lowercase() == "project manager" || role.lowercase() == "admin",
        onPinClick = { onEvent(SprintReducer.Event.PinSprintClicked) },
        onDeleteClick = { onEvent(SprintReducer.Event.DeleteSprintClicked) },
        onEditClick = { onEvent(SprintReducer.Event.EditSprintClicked) },
        onCreateClick = { onEvent(SprintReducer.Event.CreateIssueClicked) },
        onBackClick = onNavigateBack,
        modifier = modifier,
        isLoading = uiState.isLoading,
        snackbarHostState = snackbarHostState
    ) { padding ->
        KanbanBoard(
            issues = issues,
            pinnedIssues = uiState.pinnedIssues,
            onDrag = { issue -> onEvent(SprintReducer.Event.UpdateState(draggedIssue = issue)) },
            onDrop = { status -> onEvent(SprintReducer.Event.IssueDropped(status)) },
            modifier = Modifier.padding(padding),
            onPinClick = { onEvent(SprintReducer.Event.PinIssueClicked(it)) },
            onDeleteClick = { onEvent(SprintReducer.Event.DeleteIssueClicked(it)) },
            onEditClick = { onEvent(SprintReducer.Event.EditIssueClicked(it)) },
            onCommentClick = { issue ->
                commentsViewModel.setIssue(issue)
                showCommentsSheet = true
            }
        )
    }

    if (showCommentsSheet) {
        ModalBottomSheet(
            onDismissRequest = { showCommentsSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            CommentsSheetContent(
                uiState = commentsUiState,
                onCommentTextChanged = commentsViewModel::onCommentTextChanged,
                onSendClick = commentsViewModel::addComment,
                onEditClick = commentsViewModel::updateComment,
                onDeleteClick = commentsViewModel::deleteComment
            )
        }
    }
}

@Preview
@Composable
fun SprintContentPreview() {
    DevDashTheme {
        val issuesPagingItems = flowOf(PagingData.from(issueList)).collectAsLazyPagingItems()
        val commentsPagingItems = flowOf(PagingData.from(commentList)).collectAsLazyPagingItems()

        val sampleUiState = SprintReducer.State(
            sprint = sprintList[0],
            isLoading = false,
            isPinned = true,
            issues = flowOf(PagingData.from(issueList)),
            pinnedIssues = issueList.take(3),
            users = emptyList(),
            comments = flowOf(PagingData.from(commentList)),
            draggedIssue = null,
            dialog = null
        )

        SprintContent(
            role = "Project Manager",
            uiState = sampleUiState,
            issues = issuesPagingItems,
            comments = commentsPagingItems,
            onEvent = {},
            snackbarHostState = remember { SnackbarHostState() },
            onNavigateBack = {}
        )
    }
}
