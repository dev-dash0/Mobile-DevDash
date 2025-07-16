package com.elfeky.domain.repo

import com.elfeky.domain.model.join.InviteTenantRequest
import com.elfeky.domain.model.join.UpdateRoleRequest

interface InviteTenantRepo {
    suspend fun invite(accessToken: String, request: InviteTenantRequest)
    suspend fun updateRole(accessToken: String, tenantId: Int, update: UpdateRoleRequest)
}