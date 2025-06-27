package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.issue.Issue
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface IssueApiService {
    @POST("api/issue/{id}")
    suspend fun getIssue(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
    ): ServiceResponse<Issue>

    @Multipart
    @PUT("api/issue/{id}")
    suspend fun updateIssue(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
        @PartMap fields: Map<String, RequestBody>,
        @Part attachment: MultipartBody.Part?
    )

    @DELETE("api/issue/{id}")
    suspend fun deleteIssue(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
    )
}