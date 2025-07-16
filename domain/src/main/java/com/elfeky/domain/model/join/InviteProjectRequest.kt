package com.elfeky.domain.model.join

data class InviteProjectRequest(
    val email: String,
    val role: String,
    val projectId: Int
)