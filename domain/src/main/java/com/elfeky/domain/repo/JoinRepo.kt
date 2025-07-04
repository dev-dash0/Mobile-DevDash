package com.elfeky.domain.repo

import com.elfeky.domain.model.join.JoinProject
import com.elfeky.domain.model.join.JoinTenant

interface JoinRepo {
    suspend fun joinTenant(accessToken: String, tenantCode: String): JoinTenant

    suspend fun leaveTenant(accessToken: String, tenantId: Int)

    suspend fun joinProject(accessToken: String, projectCode: String): JoinProject

    suspend fun leaveProject(accessToken: String, projectId: Int)
}