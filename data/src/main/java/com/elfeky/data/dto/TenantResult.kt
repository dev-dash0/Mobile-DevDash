package com.elfeky.data.dto

import com.elfeky.domain.model.tenant.Tenant

data class TenantResult(
    val id: Int,
    val tenant: Tenant
)