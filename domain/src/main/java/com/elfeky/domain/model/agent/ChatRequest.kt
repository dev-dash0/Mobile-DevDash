package com.elfeky.domain.model.agent

import com.google.gson.annotations.SerializedName

data class ChatRequest(
    val text: String,
    val startDate: String?,
    val endDate: String?,
    @SerializedName("tenant_id") val tenantId: String,
    @SerializedName("chat_id") val chatId: String = "",
)
