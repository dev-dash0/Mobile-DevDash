package com.elfeky.devdash.ui.screens.details_screens.sprint.issue_comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.elfeky.devdash.ui.common.commentList
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.itemsPaging
import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.comment.Comment
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun CommentsSheetContent(
    comments: LazyPagingItems<Comment>,
    tempComment: Comment?,
    user: UserProfile?,
    onSendClick: (message: String) -> Unit,
    onEditClick: (Comment) -> Unit,
    onDeleteClick: (Comment) -> Unit,
    modifier: Modifier = Modifier
) {
    var messageInput by remember { mutableStateOf(TextFieldValue("")) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (commentList, commentInput, _, _) = createRefs()

//        if (comments.itemSnapshotList.isEmpty()) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .constrainAs(commentList) {
//                        top.linkTo(parent.top)
//                        bottom.linkTo(commentInput.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                        height = Dimension.fillToConstraints
//                    }
//                    .padding(16.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    "No comments to display.",
//                    color = MaterialTheme.colorScheme.onBackground,
//                    style = MaterialTheme.typography.headlineSmall
//                )
//            }
//        } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .constrainAs(commentList) {
                    top.linkTo(parent.top)
                    bottom.linkTo(commentInput.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = listState
        ) {
            itemsPaging(comments, key = { it.id }) { comment ->
                val isCurrentUser = user?.id == comment.createdById
                CommentBubble(
                    comment = comment,
                    isCurrentUser = isCurrentUser,
                    onEditClick = { onEditClick(it) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }

            item {
                if (tempComment != null) {
                    CommentBubble(
                        comment = tempComment,
                        isCurrentUser = true,
                        onEditClick = { onEditClick(it) },
                        onDeleteClick = { onDeleteClick(it) }
                    )
                }
            }
        }
        LaunchedEffect(comments.itemCount) {
            coroutineScope.launch {
                if (comments.itemCount > 0) listState.animateScrollToItem(comments.itemCount - 1)
            }
        }
//        }

        CommentInput(
            message = messageInput,
            onMessageChange = { messageInput = it },
            onSendClick = {
                onSendClick(it)
                coroutineScope.launch {
                    listState.animateScrollToItem(if (comments.itemCount == 0) 0 else comments.itemCount - 1)
                }
//                comments.refresh()
            },
            isEditing = false,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .heightIn(max = 250.dp)
                .constrainAs(commentInput) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )
    }
}

@Preview
@Composable
private fun CommentsSheetContentPreview() {
    val comments = flowOf(PagingData.from(commentList.take(3)))
    DevDashTheme {
        CommentsSheetContent(
            comments = comments.collectAsLazyPagingItems(),
            user = userList[0],
            tempComment = commentList[0].copy(id = -1),
            onSendClick = {},
            onEditClick = { },
            onDeleteClick = { }
        )
    }
}