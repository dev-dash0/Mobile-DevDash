package com.elfeky.devdash.ui.screens.main_screens.agent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.model.agent.AgentResponse
import com.elfeky.domain.repo.AgentRepo
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgentViewModel @Inject constructor(
    private val repository: AgentRepo,
    private val gson: Gson
) : ViewModel() {

    var messages = MutableStateFlow<List<String>>(emptyList())
        private set


    fun startChat(
        text: String, startDate: String, endDate: String, tenantId: String
    ) {
        viewModelScope.launch {
            repository.startSseStream(
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6InNhbXkxMiIsImp0aSI6IkpUSWYyYzI1ZjZjLTI5MGYtNDFhZS1hM2YxLTk0ZTg1NDEwZWVlZSIsImVtYWlsIjoic2FteUBnbWFpbC5jb20iLCJzdWIiOiI4MyIsIm5iZiI6MTc1MTE0MzQ3NSwiZXhwIjoxNzUxMTg2Njc1LCJpYXQiOjE3NTExNDM0NzUsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6NDQzMDYiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjQyMDAifQ.taGd4cgBhQQ1Yt1o0-i3H-GlxIbnjEY5uMKmlv3rjR0",
                text = text,
                startDate = startDate,
                endDate = endDate,
                tenantId = tenantId,
                onEvent = { event ->
                    val response = gson.fromJson(event, AgentResponse::class.java)
                    Log.d("AgentViewModel", "Response: $response")
                    when (response.type) {
                        "start" -> messages.value += "Chat started: ${response.chatId}"
                        "token" -> messages.value += "${response.content}"
                        "tool_call" -> messages.value += "Tool: ${response.toolName}"
                        "tool_output" -> messages.value += "Tool Output (${response.toolName})"
                        "end" -> messages.value += "Chat ended: ${response.chatId}"
                        "error" -> messages.value += "Error: ${response.content}"
                        else -> messages.value += "Unknown event type: $event"
                    }
                },
                onError = { error ->
                    messages.value = messages.value + "Error: ${error.message}"
                },
                onComplete = {
                    messages.value = messages.value + "Stream completed."
                }
            )
        }
    }

    fun stopChat() {
        viewModelScope.launch {
            repository.stopSseStream()
        }
    }
}