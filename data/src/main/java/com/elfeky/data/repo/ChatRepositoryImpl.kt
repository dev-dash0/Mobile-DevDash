package com.elfeky.data.repo

import com.elfeky.data.remote.AgentChatClient
import com.elfeky.domain.model.agent.ChatRequest
import com.elfeky.domain.model.agent.SSEEvent
import com.elfeky.domain.repo.ChatRepository
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val remoteDataSource: AgentChatClient,
    private val accessTokenUseCase: AccessTokenUseCase
) : ChatRepository {
    override fun startChat(request: ChatRequest): Flow<SSEEvent> {
        return remoteDataSource.startChatSession(request, accessTokenUseCase.get())
    }
}