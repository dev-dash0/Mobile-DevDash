package com.elfeky.domain.repo

import com.elfeky.domain.model.agent.ChatRequest
import com.elfeky.domain.model.agent.SSEEvent
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun startChat(request: ChatRequest): Flow<SSEEvent>
}