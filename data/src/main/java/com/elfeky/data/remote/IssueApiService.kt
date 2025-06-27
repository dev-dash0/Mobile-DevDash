package com.elfeky.data.remote

import com.elfeky.data.remote.dto.IssueResult
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
        @Part("priority") priority: RequestBody,
        @Part("status") status: RequestBody,
        @Part("title") title: RequestBody,
        @Part("type") type: RequestBody,
        @Part("description") description: RequestBody,
        @Part("isBacklog") isBacklog: RequestBody,
        @Part("startDate") startDate: RequestBody,
        @Part("deadline") deadline: RequestBody,
        @Part("deliveredDate") deliveredDate: RequestBody,
        @Part attachment: MultipartBody.Part?,
        @Part("lastUpdate") lastUpdate: RequestBody,
        @Part("labels") labels: RequestBody
    ): ServiceResponse<IssueResult>

    @DELETE("api/issue/{id}")
    suspend fun deleteIssue(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
    )
}