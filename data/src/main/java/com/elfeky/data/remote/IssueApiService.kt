package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.issue.Issue
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface IssueApiService {
    @GET("api/issue/{id}")
    suspend fun getIssue(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
    ): ServiceResponse<Issue>

    @Multipart
    @PUT("api/issue/{id}")
    suspend fun updateIssue(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
        @Part("Priority") priority: RequestBody,
        @Part("Status") status: RequestBody,
        @Part("Title") title: RequestBody,
        @Part("Type") type: RequestBody,
        @Part("Description") description: RequestBody?,
        @Part("IsBacklog") isBacklog: RequestBody,
        @Part("StartDate") startDate: RequestBody?,
        @Part("Deadline") deadline: RequestBody?,
        @Part("DeliveredDate") deliveredDate: RequestBody?,
        @Part attachment: MultipartBody.Part?,
        @Part("LastUpdate") lastUpdate: RequestBody?,
        @Part("Labels") labels: RequestBody?,
        @Part("SprintId") sprintId: RequestBody?
    )

    @DELETE("api/issue/{id}")
    suspend fun deleteIssue(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
    )
}