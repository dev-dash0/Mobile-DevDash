package com.elfeky.data.remote.dto

 class AgentRequestDto(
    val tenant_id: String,
    val text: String,
    val startDate: String,
    val endDate: String,
    val chat_id: String = ""
)