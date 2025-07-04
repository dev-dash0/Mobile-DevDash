package com.elfeky.devdash.ui.screens.details_screens.sprint.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.elfeky.devdash.ui.theme.DevDashTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

// Data class for a comment, now includes a unique ID and an optional parentId for replies
data class Comment(
    val id: String = UUID.randomUUID().toString(), // Unique ID for each comment
    val author: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    var likes: Int = 0,
    val parentId: String? = null // Null for top-level comments, ID of parent for replies
)

@Composable
fun CommentSection(modifier: Modifier = Modifier) {
    val comments = remember {
        mutableStateListOf<Comment>(
            Comment(
                author = "User 1",
                text = "Initial comment"
            )
        )
    }
    var newCommentText by remember { mutableStateOf("") }
    var currentAuthor by remember { mutableStateOf("User 1") } // Placeholder for current user

    // State to hold the comment being replied to
    var replyingToComment by remember { mutableStateOf<Comment?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Issue Comments",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            if (comments.isEmpty()) {
                item {
                    Text(
                        text = "No comments yet. Be the first to add one!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        color = Color.Gray
                    )
                }
            }
            items(comments) { comment ->
                // Find the parent author for display if this comment is a reply
                val parentAuthor = if (comment.parentId != null) {
                    comments.find { it.id == comment.parentId }?.author
                } else {
                    null
                }

                CommentItem(
                    comment = comment,
                    parentAuthor = parentAuthor, // Pass parent author for display
                    onLikeClick = { clickedComment ->
                        // Find the index of the clicked comment and update its likes count
                        val index = comments.indexOf(clickedComment)
                        if (index != -1) {
                            comments[index] =
                                comments[index].copy(likes = comments[index].likes + 1)
                        }
                    },
                    onReplyClick = { commentToReplyTo ->
                        replyingToComment = commentToReplyTo
                    },
                    // Apply left padding for replies to simulate nesting
                    modifier = Modifier.padding(start = if (comment.parentId != null) 24.dp else 0.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input field for new comments/replies
        ChatBox {
            if (newCommentText.isNotBlank()) {
                val newComment = if (replyingToComment != null) {
                    Comment(
                        author = currentAuthor,
                        text = newCommentText.trim(),
                        parentId = replyingToComment!!.id // Set parentId for replies
                    )
                } else {
                    Comment(
                        author = currentAuthor,
                        text = newCommentText.trim()
                    )
                }
                comments.add(newComment)
                newCommentText = ""
                replyingToComment = null // Clear reply state after posting
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBox(
    modifier: Modifier = Modifier,
    onSendChatClickListener: (String) -> Unit
) {
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        IconButton(
            onClick = {
                val msg = chatBoxValue.text
                if (msg.isBlank()) return@IconButton
                onSendChatClickListener(chatBoxValue.text)
                chatBoxValue = TextFieldValue("")
            },
            enabled = chatBoxValue.text.isNotBlank(),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun ChatScreen(modifier: Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (_, _) = createRefs()
    }
}

@Composable
fun CommentItem(
    comment: Comment,
    parentAuthor: String?, // New parameter for parent author
    onLikeClick: (Comment) -> Unit,
    onReplyClick: (Comment) -> Unit, // New parameter for reply click
    modifier: Modifier = Modifier
) {
    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = comment.author,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )

            // Display "Replying to @ParentAuthor" if it's a reply
            if (parentAuthor != null) {
                Text(
                    text = "Replying to @$parentAuthor",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            val annotatedCommentText = buildAnnotatedString {
                val mentionStyle = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )

                val words = comment.text.split(" ", "\n", "\t")
                words.forEachIndexed { index, word ->
                    if (word.startsWith("@") && word.length > 1) {
                        withStyle(mentionStyle) {
                            append(word)
                        }
                    } else {
                        append(word)
                    }
                    if (index < words.size - 1) {
                        append(" ")
                    }
                }
            }

            Text(
                text = annotatedCommentText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = dateFormatter.format(Date(comment.timestamp)),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onLikeClick(comment) }) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Like",
                            tint = if (comment.likes > 0) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                    Text(
                        text = comment.likes.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { onReplyClick(comment) }) {
                        Text("Reply")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCommentSection() {
    DevDashTheme {
        CommentSection()
    }
}

// You would typically call CommentSection from your main Activity's composable, e.g.:
/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CommentSection()
                }
            }
        }
    }
}
*/
