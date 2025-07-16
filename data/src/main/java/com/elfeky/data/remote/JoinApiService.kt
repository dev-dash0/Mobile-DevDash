package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.join.InviteProjectRequest
import com.elfeky.domain.model.join.InviteTenantRequest
import com.elfeky.domain.model.join.JoinProject
import com.elfeky.domain.model.join.JoinTenant
import com.elfeky.domain.model.join.UpdateRoleRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface JoinApiService {
    @POST("/api/UserTenant/invite")
    suspend fun inviteTenant(
        @Header("Authorization") accessToken: String,
        @Body body: InviteTenantRequest,
    ): ServiceResponse<Nothing>

    @PATCH("api/UserTenant/update-role")
    suspend fun updateUserTenantRole(
        @Header("Authorization") accessToken: String,
        @Query("tenantId") tenantId: Int,
        @Body requestBody: UpdateRoleRequest
    ): ServiceResponse<Nothing>

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

    /* -------------------------------------------------------------------------------- */

    @POST("/api/UserProject/invite")
    suspend fun inviteProject(
        @Header("Authorization") accessToken: String,
        @Body body: InviteProjectRequest,
    ): ServiceResponse<Nothing>

    @PATCH("api/UserProject/update-role")
    suspend fun updateUserProjectRole(
        @Header("Authorization") accessToken: String,
        @Query("ProjectId") tenantId: Int,
        @Body requestBody: UpdateRoleRequest
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