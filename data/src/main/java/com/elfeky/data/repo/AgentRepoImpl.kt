package com.elfeky.data.repo

import android.util.Log
import com.elfeky.data.remote.dto.AgentRequestDto
import com.elfeky.data.remote.sse.SseClient
import com.elfeky.domain.repo.AgentRepo
import com.elfeky.domain.util.Constants.AGENT_BASE_URL
import com.google.gson.Gson
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class AgentRepoImpl @Inject constructor(
    private val gson: Gson,
    private val sseClient: SseClient
) : AgentRepo {

    private val baseUrl = AGENT_BASE_URL

    override  fun startSseStream(
        text: String,
        startDate: String,
        endDate: String,
        tenantId: String,
        token: String,
        onEvent:  (String) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit
    ) {
        val request = AgentRequestDto(
            tenant_id = tenantId,
            text = text,
            startDate = startDate,
            endDate = endDate,
            chat_id = ""
        )
        val json = gson.toJson(request, AgentRequestDto::class.java)
        val body = json.toRequestBody("application/json".toMediaType())


        Log.d("AgentRepoImpl", "Request JSON: $json")
        Log.d("AgentRepoImpl", "Request JSON: $body")
        sseClient.start(
            url = baseUrl,
            requestBody = body,
            token = token,
            onEvent = onEvent,
            onError = onError,
            onComplete = onComplete
        )
    }

    override  fun stopSseStream() {
        sseClient.stop()
    }
}