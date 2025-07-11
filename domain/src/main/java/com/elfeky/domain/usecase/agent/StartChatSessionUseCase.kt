package com.elfeky.domain.usecase.agent

import com.elfeky.domain.model.agent.ChatRequest
import com.elfeky.domain.model.agent.SSEEvent
import com.elfeky.domain.repo.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartChatSessionUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke(request: ChatRequest): Flow<SSEEvent> {
        return repository.startChat(request)
    }
}