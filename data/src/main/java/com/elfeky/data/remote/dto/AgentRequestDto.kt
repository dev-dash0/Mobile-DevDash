package com.elfeky.data.remote.dto

import com.google.gson.annotations.SerializedName

class AgentRequestDto(
    @SerializedName("tenant_id") val tenantId: String,
    val text: String,
    val startDate: String?,
    val endDate: String?,
    @SerializedName("chat_id") val chatId: String = ""
)