package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.data.remote.dto.TenantResult
import com.elfeky.domain.model.tenant.Tenant
import com.elfeky.domain.model.tenant.TenantRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TenantApiService {
    @POST("/api/Tenant")
    suspend fun addCompany(
        @Header("Authorization") accessToken: String,
        @Body request: TenantRequest
    ): ServiceResponse<TenantResult>

    @GET("/api/Tenant")
    fun getCompanies(@Header("Authorization") accessToken: String): ServiceResponse<List<Tenant>>

    @GET("/api/Tenant/{tenantId}")
    fun getCompanyById(
        @Header("Authorization") accessToken: String,
        @Path("tenantId") tenantId: Int
    ): ServiceResponse<Tenant>

    @PUT("/api/Tenant/{tenantId}")
    fun updateCompany(
        @Header("Authorization") accessToken: String,
        @Path("tenantId") tenantId: Int,
        @Body request: TenantRequest
    ): ServiceResponse<Unit>

    @DELETE("/api/Tenant/{tenantId}")
    fun deleteCompany(
        @Header("Authorization") accessToken: String,
        @Path("tenantId") tenantId: Int
    ): ServiceResponse<Unit>
}