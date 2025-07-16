package com.elfeky.data.repo

import com.elfeky.data.remote.JoinApiService
import com.elfeky.domain.model.join.InviteProjectRequest
import com.elfeky.domain.model.join.UpdateRoleRequest
import com.elfeky.domain.repo.InviteProjectRepo
import javax.inject.Inject

class InviteProjectRepoImpl @Inject constructor(
    val api: JoinApiService
) : InviteProjectRepo {
    override suspend fun invite(
        accessToken: String,
        request: InviteProjectRequest
    ) {
        api.inviteProject("Bearer $accessToken", request)
    }

    override suspend fun updateRole(
        accessToken: String,
        projectId: Int,
        body: UpdateRoleRequest
    ) {
        api.updateUserProjectRole("Bearer $accessToken", projectId, body)
    }
}