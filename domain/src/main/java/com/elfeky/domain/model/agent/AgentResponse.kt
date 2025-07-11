package com.elfeky.domain.model.agent

import com.google.gson.annotations.SerializedName

data class AgentResponse(
    val type: String,
    val content: String? = null,
    @SerializedName("chat_id") val chatId: String? = null,
    @SerializedName("tool_name") val toolName: String? = null
)