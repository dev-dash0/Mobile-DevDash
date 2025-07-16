package com.elfeky.domain.repo

import com.elfeky.domain.model.join.InviteProjectRequest
import com.elfeky.domain.model.join.UpdateRoleRequest

interface InviteProjectRepo {
    suspend fun invite(accessToken: String, request: InviteProjectRequest)
    suspend fun updateRole(accessToken: String, projectId: Int, body: UpdateRoleRequest)
}