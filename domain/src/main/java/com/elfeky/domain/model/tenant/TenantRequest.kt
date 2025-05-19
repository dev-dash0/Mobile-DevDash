package com.elfeky.domain.model.tenant

data class TenantRequest(
    val description: String,
    val image: String?,
    val keywords: String,
    val name: String,
    val tenantUrl: String
)