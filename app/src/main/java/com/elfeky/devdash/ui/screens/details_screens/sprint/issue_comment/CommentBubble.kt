package com.elfeky.devdash.ui.screens.details_screens.sprint.issue_comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.commentList
import com.elfeky.devdash.ui.common.component.avatar.Avatar
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.comment.Comment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CommentBubble(
    comment: Comment,
    onEditClick: (Comment) -> Unit,
    onDeleteClick: (Comment) -> Unit,
    modifier: Modifier = Modifier,
    isCurrentUser: Boolean = false
) {
    val backgroundColor =
        if (isCurrentUser) MaterialTheme.colorScheme.surfaceContainer
        else MaterialTheme.colorScheme.surfaceContainerHigh

    val textColor =
        if (isCurrentUser) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurface

    val bubbleShape =
        if (isCurrentUser) RoundedCornerShape(8.dp, 0.dp, 8.dp, 8.dp)
        else RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!isCurrentUser) {
            Avatar(comment.createdBy, size = 24.dp)
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = modifier.widthIn(max = 300.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start,
        ) {
            Card(
                shape = bubbleShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(backgroundColor)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (!isCurrentUser) {
                        Text(
                            text = comment.createdBy.userName,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            color = getUserAccentColor(comment.createdBy.userName)
                        )
                    }

                    Text(
                        text = comment.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = LocalDateTime.parse(
                                comment.creationDate,
                                DateTimeFormatter.ISO_LOCAL_DATE_TIME
                            ).format(DateTimeFormatter.ofPattern("HH:mm a")),
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor.copy(alpha = .6f)
                        )
                        Spacer(modifier = Modifier.width(4.dp)) // Small space between text and icon

                        if (isCurrentUser) {
                            if (comment.id < 0) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = "Pending",
                                    tint = textColor.copy(alpha = .6f),
                                    modifier = Modifier.size(12.dp) // Adjust size as needed
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Check, // Or Icons.Default.Done
                                    contentDescription = "Sent",
                                    tint = textColor.copy(alpha = .6f), // A subtle primary color for sent
                                    modifier = Modifier.size(12.dp) // Adjust size as needed
                                )
                            }
                        }
                    }
                }
            }

            if (isCurrentUser) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    TextButton(onClick = { onEditClick(comment) }) {
                        Text("edit", color = MaterialTheme.colorScheme.secondary)
                    }
                    TextButton(onClick = { onDeleteClick(comment) }) {
                        Text("delete", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CommentBubblePreview_CurrentUser() {
    DevDashTheme {
        Row(modifier = Modifier.fillMaxWidth()) {
            CommentBubble(
                comment = commentList[0],
                onEditClick = {},
                onDeleteClick = {},
                isCurrentUser = true
            )
        }
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