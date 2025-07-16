package com.elfeky.devdash.ui.screens.details_screens.sprint.components.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.elfeky.devdash.ui.common.commentList
import com.elfeky.devdash.ui.common.component.avatar.Avatar
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.itemsPaging
import com.elfeky.domain.model.comment.Comment
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@Composable
fun CommentsSheetContent(
    uiState: CommentsViewModel.CommentsUiState,
    onCommentTextChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    onEditClick: (Comment) -> Unit,
    onDeleteClick: (Comment) -> Unit,
    modifier: Modifier = Modifier
) {
    val comments = uiState.comments.collectAsLazyPagingItems()

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (commentList, commentInput, load) = createRefs()

        if (comments.itemSnapshotList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(commentList) {
                        top.linkTo(parent.top)
                        bottom.linkTo(commentInput.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No comments to display.",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .constrainAs(commentList) {
                        top.linkTo(parent.top)
                        bottom.linkTo(load.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    },
                verticalArrangement = Arrangement.spacedBy(8.dp),
                reverseLayout = true
            ) {
                itemsPaging(comments) { comment ->
                    CommentBubble(
                        comment = comment,
                        onEditClick = { onEditClick(it) },
                        onDeleteClick = { onDeleteClick(it) }
                    )
                }
            }

            CircularProgressIndicator(
                modifier = Modifier
                    .size(if (uiState.isLoading) 50.dp else 0.dp)
                    .padding(16.dp)
                    .constrainAs(load) {
                        top.linkTo(commentList.bottom)
                        bottom.linkTo(commentInput.top)
                        start.linkTo(parent.start)
                    },
                color = MaterialTheme.colorScheme.secondary
            )
        }

        CommentInput(
            text = uiState.commentInputText,
            onTextChange = onCommentTextChanged,
            onSendClick = onSendClick,
            isEditing = uiState.editingComment != null,
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

@Composable
fun CommentBubble(
    comment: Comment,
    onEditClick: (Comment) -> Unit,
    onDeleteClick: (Comment) -> Unit,
    modifier: Modifier = Modifier,
    isCurrentUser: Boolean = false
) {
    val backgroundColor =
        if (isCurrentUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val bubbleShape =
        if (isCurrentUser) {
            RoundedCornerShape(
                topStart = 16.dp,
                bottomStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 4.dp
            )
        } else {
            RoundedCornerShape(
                topStart = 16.dp,
                bottomStart = 4.dp,
                topEnd = 16.dp,
                bottomEnd = 16.dp
            )
        }

    Row(
        modifier = modifier
            .widthIn(max = 300.dp)
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isCurrentUser) {
            Avatar(comment.createdBy, size = 24.dp)
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start,
        ) {
            Card(
                shape = bubbleShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(backgroundColor)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (!isCurrentUser) {
                        Text(
                            text = comment.createdBy.userName,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = getUserAccentColor(comment.createdBy.id)
                        )
                    }

                    Text(
                        text = comment.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
//                        text = "",
                        text = LocalDateTime.parse(
                            comment.creationDate,
                            DateTimeFormatter.ISO_LOCAL_DATE_TIME
                        ).format(DateTimeFormatter.ofPattern("HH:mm")),
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            .let {
                                if (isCurrentUser) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                                else it
                            }
                    )
                }
            }

            if (isCurrentUser) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = { onEditClick(comment) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Comment",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(
                        onClick = { onDeleteClick(comment) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete, contentDescription = "Delete Comment",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun getUserAccentColor(userId: Int): Color {
    val hashCode = userId.hashCode()
    val hue = (abs(hashCode) % 360).toFloat()
    return Color.hsl(
        hue = hue,
        saturation = 1f,
        lightness = 1f
    )
}

@Preview
@Composable
private fun CommentBubblePreview_CurrentUser() {
    DevDashTheme {
        CommentBubble(
            comment = commentList[0],
            onEditClick = {},
            onDeleteClick = {},
            isCurrentUser = true
        )
    }
}

@Preview
@Composable
private fun CommentBubblePreview_OtherUser() {
    DevDashTheme {
        CommentBubble(
            comment = commentList[1],
            onEditClick = {},
            onDeleteClick = {},
            isCurrentUser = false
        )
    }
}


@Composable
private fun CommentInput(
    text: String,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    isEditing: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text(if (isEditing) "Edit comment..." else "Add a comment...") },
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondary
            )
        )
        IconButton(
            onClick = onSendClick,
            enabled = text.isNotBlank(),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                disabledContentColor = MaterialTheme.colorScheme.outline
            )
        ) {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send Message")
        }
    }
}

@Preview
@Composable
private fun CommentsSheetContentPreview() {
    val comments = flowOf(PagingData.from(commentList))
    DevDashTheme {
        CommentsSheetContent(
            uiState = CommentsViewModel.CommentsUiState(
                issue = issueList[0],
                comments = comments
            ),
            onCommentTextChanged = {},
            onSendClick = {},
            onEditClick = { },
            onDeleteClick = { }
        )
    }
}

@Preview
@Composable
private fun CommentInputPreview() {
    DevDashTheme {
        CommentInput(
            text = "Some comment text",
            onTextChange = {},
            onSendClick = {},
            isEditing = true
        )
    }
}
