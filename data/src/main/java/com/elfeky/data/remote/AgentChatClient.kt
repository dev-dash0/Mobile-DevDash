package com.elfeky.data.remote

import android.util.Log
import com.elfeky.domain.model.agent.AgentResponse
import com.elfeky.domain.model.agent.ChatRequest
import com.elfeky.domain.model.agent.SSEEvent
import com.elfeky.domain.util.Constants.AGENT_BASE_URL
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import javax.inject.Inject

class AgentChatClient @Inject constructor(
    private val client: OkHttpClient,
    private val gson: Gson
) {
    fun startChatSession(chatRequest: ChatRequest, token: String): Flow<SSEEvent> = callbackFlow {
        val requestJson = gson.toJson(chatRequest)
        val request = Request.Builder()
            .url(AGENT_BASE_URL)
            .post(requestJson.toRequestBody("application/json".toMediaType()))
            .header("Accept", "text/event-stream")
            .addHeader("Authorization", "Bearer $token")
            .build()

        val listener = object : EventSourceListener() {
            override fun onEvent(
                eventSource: EventSource,
                id: String?,
                type: String?,
                data: String
            ) {
                if (data.isBlank()) return

                try {
                    val response = gson.fromJson(data, AgentResponse::class.java)
                    val event: SSEEvent? = when (response.type) {
                        "start" -> response.chatId?.let { SSEEvent.Start(it) }
                        "token" -> response.content?.let { SSEEvent.Token(it) }
                        "tool_call" -> if (response.toolName != null) SSEEvent.ToolCall(response.toolName!!) else null
                        "tool_output" -> if (response.toolName != null) SSEEvent.ToolOutput(
                            response.toolName!!
                        ) else null

                        "end" -> response.chatId?.let { SSEEvent.End(it) }
                        "error" -> response.content?.let { SSEEvent.Error(it) }
                        else -> null
                    }

                    event?.let { trySend(it) }
                } catch (e: Exception) {
                    Log.d("AgentChatClient", "Failed to parse server event: $data")
                    trySend(SSEEvent.Error("Failed to parse server event: ${e.message}"))
                }
            }

            override fun onClosed(eventSource: EventSource) {
                trySend(SSEEvent.Complete)
                channel.close()
            }

            override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
                trySend(SSEEvent.Error(t?.message ?: "Unknown SSE error"))
                channel.close()
            }
        }

        val eventSource = EventSources.createFactory(client).newEventSource(request, listener)

        awaitClose {
            eventSource.cancel()
        }
    }
}