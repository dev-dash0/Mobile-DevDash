package com.elfeky.domain.model.join

data class InviteTenantRequest(
    val email: String,
    val role: String,
    val tenantId: Int
)