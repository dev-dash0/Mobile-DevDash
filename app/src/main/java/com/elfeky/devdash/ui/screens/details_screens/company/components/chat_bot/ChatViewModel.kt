package com.elfeky.devdash.ui.screens.details_screens.company.components.chat_bot

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.model.agent.ChatRequest
import com.elfeky.domain.model.agent.SSEEvent
import com.elfeky.domain.usecase.agent.StartChatSessionUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

data class ChatState(
    val messages: List<Message> = emptyList(),
    val inputText: String = "",
    val isAwaitingResponse: Boolean = false,
    var chatId: String? = null
)

@HiltViewModel(assistedFactory = ChatViewModel.Factory::class)
class ChatViewModel @AssistedInject constructor(
    @Assisted private val companyId: Int,
    private val startChatSessionUseCase: StartChatSessionUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(tenantId: Int): ChatViewModel
    }

    private val _chatState = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState.asStateFlow()

    fun onInputTextChanged(newText: String) {
        _chatState.update { it.copy(inputText = newText) }
    }

    fun sendMessage() {
        val text = _chatState.value.inputText
        if (text.isBlank() || _chatState.value.isAwaitingResponse) return

        val userMessage = Message(text, Sender.USER)
        _chatState.update {
            it.copy(
                messages = it.messages + userMessage,
                inputText = "",
                isAwaitingResponse = true
            )
        }

        val request = ChatRequest(
            text = text,
            chatId = _chatState.value.chatId ?: "",
            tenantId = companyId.toString(),
            startDate = null,
            endDate = null,
        )

        startChatSessionUseCase(request)
            .onEach { event -> handleSseEvent(event) }
            .catch { e ->
                val errorMessage = Message("Error: ${e.message}", Sender.AI)
                _chatState.update {
                    it.copy(messages = it.messages + errorMessage, isAwaitingResponse = false)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleSseEvent(event: SSEEvent) {
        when (event) {
            is SSEEvent.Start -> {
                Log.d("ChatViewModel", "Start: ${event.chatId}")
                _chatState.update { it.copy(chatId = event.chatId) }
                val aiMessage = Message("", Sender.AI)
                _chatState.update { it.copy(messages = it.messages + aiMessage) }
            }

            is SSEEvent.Token -> {
                _chatState.update {
                    val updatedMessages = it.messages.toMutableList()
                    Log.d("ChatViewModel", "Token: ${event.content}")
                    val lastMessage = updatedMessages.last()
                    updatedMessages[updatedMessages.size - 1] = lastMessage.copy(
                        text = lastMessage.text + event.content
                    )
                    it.copy(messages = updatedMessages)
                }
            }

            is SSEEvent.End -> {
                Log.d("ChatViewModel", "End: ${event.chatId}")
                _chatState.update { it.copy(isAwaitingResponse = false) }
            }

            is SSEEvent.Error -> {
                Log.d("ChatViewModel", "Error: ${event.content}")
                val errorMessage = Message(event.content, Sender.AI)
                _chatState.update {
                    it.copy(messages = it.messages + errorMessage, isAwaitingResponse = false)
                }
            }

            is SSEEvent.Complete -> {
                Log.d("ChatViewModel", "Complete")
                val completeMessage = Message("Done", Sender.AI)
                _chatState.update {
                    it.copy(
                        messages = it.messages + completeMessage,
                        isAwaitingResponse = false
                    )
                }
            }

            is SSEEvent.ToolCall -> {
                Log.d("ChatViewModel", "ToolCall: ${event.toolName}")
            }

            is SSEEvent.ToolOutput -> {
                Log.d("ChatViewModel", "ToolOutput: ${event.toolName}")
            }
        }
    }
}
