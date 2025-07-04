package com.elfeky.domain.model.join

data class JoinTenant(
    val joinedDate: String,
    val role: String,
    val tenantId: Int,
    val userId: Int
)