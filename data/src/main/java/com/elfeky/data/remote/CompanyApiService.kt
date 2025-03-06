package com.elfeky.data.remote

import com.elfeky.data.dto.company.GetCompaniesResponse
import com.elfeky.domain.model.CompanyRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface CompanyApiService {
    @GET("/api/Tenant")
    suspend fun addCompany(
        @Header("Authorization") accessToken: String,
        @Body request: CompanyRequest
    )

    @GET("/api/Tenant")
    fun getCompanies(@Header("Authorization") accessToken: String): GetCompaniesResponse

}