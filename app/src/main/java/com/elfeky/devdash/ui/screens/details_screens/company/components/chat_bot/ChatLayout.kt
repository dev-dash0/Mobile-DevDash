package com.elfeky.devdash.ui.screens.details_screens.company.components.chat_bot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun ChatLayout(
    state: ChatState,
    onInputTextChange: (String) -> Unit,
    onSendMessage: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (messageList, messageInput, load) = createRefs()

        if (state.messages.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(messageList) {
                        top.linkTo(parent.top)
                        bottom.linkTo(messageInput.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Ask me anything!",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        } else {
            MessageList(
                messages = state.messages,
                modifier = Modifier.constrainAs(messageList) {
                    top.linkTo(parent.top)
                    bottom.linkTo(load.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
            )

            CircularProgressIndicator(
                modifier = Modifier
                    .size(if (state.isAwaitingResponse) 50.dp else 0.dp)
                    .padding(16.dp)
                    .constrainAs(load) {
                        top.linkTo(messageList.bottom)
                        bottom.linkTo(messageInput.top)
                        start.linkTo(parent.start)
                    },
                color = MaterialTheme.colorScheme.secondary
            )
        }

        MessageInput(
            inputText = state.inputText,
            onInputTextChange = onInputTextChange,
            onSendMessage = onSendMessage,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .heightIn(max = 250.dp)
                .constrainAs(messageInput) {
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
fun ChatLayoutEmptyPreview() {
    DevDashTheme {
        ChatLayout(
            state = ChatState(),
            onInputTextChange = {},
            onSendMessage = {}
        )
    }
}

@Preview
@Composable
fun ChatLayoutPreview() {
    DevDashTheme {
        ChatLayout(
            state = ChatState(
                messages = listOf(
                    Message("Hello", Sender.USER),
                    Message("Hi, how can I help you?", Sender.AI)
                ),
                inputText = "Type here...",
                isAwaitingResponse = true
            ),
            onInputTextChange = {},
            onSendMessage = {}
        )
    }
}

