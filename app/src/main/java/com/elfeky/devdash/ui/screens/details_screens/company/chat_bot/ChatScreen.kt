package com.elfeky.devdash.ui.screens.details_screens.company.chat_bot

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
    val chatState by viewModel.chatState.collectAsStateWithLifecycle()

    ChatLayout(
        state = chatState,
        onInputTextChange = viewModel::onInputTextChanged,
        onSendMessage = viewModel::sendMessage
    )
}
