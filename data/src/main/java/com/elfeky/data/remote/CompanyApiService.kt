package com.elfeky.data.remote

import com.elfeky.data.dto.company.GetCompaniesResponse
import com.elfeky.domain.model.CompanyRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface CompanyApiService {
    @GET("/api/Tenant")
    suspend fun addCompany(
        @Header("Authorization") accessToken: String,
        @Body request: CompanyRequest
    )

    @GET("/api/Tenant")
    fun getCompanies(@Header("Authorization") accessToken: String): GetCompaniesResponse

    @GET("/api/Tenant")
    fun getCompanyById(
        @Header("Authorization") accessToken: String,
        @Query("tenantId") id: Int
    ): GetCompaniesResponse

    @PUT("/api/Tenant")
    fun updateCompany(
        @Header("Authorization") accessToken: String,
        @Query("tenantId") id: Int,
        @Body request: CompanyRequest
    )

    @DELETE("/api/Tenant")
    fun deleteCompany(
        @Header("Authorization") accessToken: String,
        @Query("tenantId") id: Int
    )

}