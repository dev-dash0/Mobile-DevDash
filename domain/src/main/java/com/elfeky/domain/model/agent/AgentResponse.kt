package com.elfeky.domain.model.agent

data class AgentResponse(
    val type: String,
    val chat_id: String? = null,
    val content: String? = null,
    val tool_name: String? = null,
    //val input: Map<String, Any>? = null,
    val output: String? = null
)