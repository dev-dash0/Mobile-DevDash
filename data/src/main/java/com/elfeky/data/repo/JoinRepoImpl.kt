package com.elfeky.data.repo

import com.elfeky.data.remote.JoinApiService
import com.elfeky.domain.model.join.JoinProject
import com.elfeky.domain.model.join.JoinTenant
import com.elfeky.domain.repo.JoinRepo
import javax.inject.Inject

class JoinRepoImpl @Inject constructor(
    private val apiService: JoinApiService
) : JoinRepo {
    override suspend fun joinTenant(
        accessToken: String,
        tenantCode: String
    ): JoinTenant = apiService.joinTenant("Bearer $accessToken", tenantCode).result

    override suspend fun leaveTenant(accessToken: String, tenantId: Int) {
        apiService.leaveTenant("Bearer $accessToken", tenantId)
    }

    override suspend fun joinProject(
        accessToken: String,
        projectCode: String
    ): JoinProject = apiService.joinProject("Bearer $accessToken", projectCode).result

    override suspend fun leaveProject(accessToken: String, projectId: Int) {
        apiService.leaveProject("Bearer $accessToken", projectId)
    }
}