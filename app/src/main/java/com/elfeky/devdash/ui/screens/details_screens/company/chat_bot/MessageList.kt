package com.elfeky.devdash.ui.screens.details_screens.company.chat_bot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.DevDashTheme

enum class Sender {
    USER, AI
}

data class Message(
    val text: String,
    val sender: Sender
)

@Composable
fun MessageList(
    messages: List<Message>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        reverseLayout = true
    ) {
        items(messages.reversed()) { message ->
            MessageItem(message = message)
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    val isUser = message.sender == Sender.USER
    val alignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        contentAlignment = alignment,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        if (isUser) {
            val backgroundColor = MaterialTheme.colorScheme.primary
            val bubbleShape = RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
            Column(
                modifier = Modifier
                    .background(color = backgroundColor, shape = bubbleShape)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .widthIn(max = 300.dp)
            ) {
                Text(text = message.text, color = MaterialTheme.colorScheme.onBackground)
            }
        } else {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth()
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(
    inputText: String,
    onInputTextChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = onInputTextChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = onSendMessage,
                enabled = inputText.isNotBlank(),
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
}

@Preview
@Composable
fun MessageInputPreview() {
    DevDashTheme {
        MessageInput(
            inputText = "Hello",
            onInputTextChange = {},
            onSendMessage = {}
        )
    }
}

@Preview
@Composable
fun MessageItemAIPreview() {
    DevDashTheme {
        MessageItem(
            message = Message(
                "Hi there! I'm a large language model, trained by Google.",
                Sender.AI
            )
        )
    }
}

@Preview
@Composable
fun MessageItemUserPreview() {
    DevDashTheme {
        MessageItem(message = Message("Hello", Sender.USER))
    }
}

@Preview
@Composable
fun MessageListPreview() {
    val messages = listOf(
        Message("Hello", Sender.USER),
        Message("Hi there!", Sender.AI),
        Message("How are you?", Sender.USER),
        Message(
            "I'm doing great, thanks for asking! I'm a large language model, trained by Google.",
            Sender.AI
        ),
    )

    DevDashTheme {
        MessageList(messages = messages)
    }
}
