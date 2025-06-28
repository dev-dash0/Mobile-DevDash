package com.elfeky.domain.model.join

data class JoinProject(
    val joinedDate: String,
    val projectId: Int,
    val role: String,
    val userId: Int
)