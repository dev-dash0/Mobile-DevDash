package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.join.JoinProject
import com.elfeky.domain.model.join.JoinTenant
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface JoinApiService {
    @POST("/api/UserTenant")
    suspend fun joinTenant(
        @Header("Authorization") accessToken: String,
        @Query("tenantCode") tenantCode: String,
    ): ServiceResponse<JoinTenant>

    @DELETE("/api/UserTenant/{tenantId}")
    suspend fun leaveTenant(
        @Header("Authorization") accessToken: String,
        @Path("tenantId") tenantId: Int,
    ): ServiceResponse<Nothing>

    @POST("/api/UserProject")
    suspend fun joinProject(
        @Header("Authorization") accessToken: String,
        @Query("projectCode") projectCode: String,
    ): ServiceResponse<JoinProject>

    @DELETE("/api/UserProject/{projectId}")
    suspend fun leaveProject(
        @Header("Authorization") accessToken: String,
        @Path("projectId") projectId: Int,
    ): ServiceResponse<Nothing>
}