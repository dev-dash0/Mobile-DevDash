package com.elfeky.domain.model.agent

sealed class SSEEvent {
    data class Start(val chatId: String) : SSEEvent()
    data class Token(val content: String) : SSEEvent()
    data class ToolCall(val toolName: String) : SSEEvent()
    data class ToolOutput(val toolName: String) : SSEEvent()
    data class End(val chatId: String) : SSEEvent()
    data class Error(val content: String) : SSEEvent()
    object Complete : SSEEvent()
}
