package com.elfeky.data.remote

import com.elfeky.data.remote.dto.IssueResult
import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.issue.Issue
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface BacklogApiService {
    @Multipart
    @POST("/api/Issue/backlog")
    suspend fun createBacklogIssue(
        @Header("Authorization") accessToken: String,
        @Query("projectId") projectId: Int,
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

    @GET("/api/Issue/backlog")
    suspend fun getBacklogIssues(
        @Header("Authorization") accessToken: String,
        @Query("projectId") projectId: Int,
        @Query("pageSize") pageSize: Int,
        @Query("pageNumber") pageNumber: Int
    ): ServiceResponse<List<Issue>>

}