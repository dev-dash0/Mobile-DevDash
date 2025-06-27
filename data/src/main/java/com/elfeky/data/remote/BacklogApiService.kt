package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.issue.Issue
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface BacklogApiService {
    @POST("/api/Issue/backlog")
    suspend fun createBacklogIssue(
        @Header("Authorization") accessToken: String,
        @Query("projectId") projectId: Int,
        @PartMap fields: Map<String, RequestBody>,
        @Part attachment: MultipartBody.Part?
    )

    @GET("/api/Issue/backlog")
    suspend fun getBacklogIssues(
        @Header("Authorization") accessToken: String,
        @Query("projectId") projectId: Int,
        @Query("pageSize") pageSize: Int,
        @Query("pageNumber") pageNumber: Int
    ): ServiceResponse<List<Issue>>

}