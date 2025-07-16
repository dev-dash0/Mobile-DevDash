package com.elfeky.data.repo

import com.elfeky.data.remote.JoinApiService
import com.elfeky.domain.model.join.InviteTenantRequest
import com.elfeky.domain.model.join.UpdateRoleRequest
import com.elfeky.domain.repo.InviteTenantRepo
import javax.inject.Inject

class InviteTenantRepoImpl @Inject constructor(
    val api: JoinApiService
) : InviteTenantRepo {
    override suspend fun invite(
        accessToken: String,
        request: InviteTenantRequest
    ) {
        api.inviteTenant("Bearer $accessToken", request)
    }

    override suspend fun updateRole(
        accessToken: String,
        projectId: Int,
        body: UpdateRoleRequest
    ) {
        api.updateUserProjectRole("Bearer $accessToken", projectId, body)
    }
}