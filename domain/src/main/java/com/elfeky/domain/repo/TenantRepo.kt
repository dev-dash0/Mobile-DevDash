package com.elfeky.domain.repo

import com.elfeky.domain.model.tenant.Tenant
import com.elfeky.domain.model.tenant.TenantRequest

interface TenantRepo {
    suspend fun createTenant(accessToken: String, request: TenantRequest):Tenant
    suspend fun getTenants(accessToken: String): List<Tenant>
    suspend fun getTenantById(accessToken: String, id: Int): Tenant
    suspend fun updateTenant(accessToken: String, id: Int, request: TenantRequest)
    suspend fun deleteTenant(accessToken: String, id: Int)
}