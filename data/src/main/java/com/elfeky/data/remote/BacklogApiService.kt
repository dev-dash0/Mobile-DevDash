package com.elfeky.data.remote

import com.elfeky.domain.model.backlog.RequestIssueBacklog
import com.elfeky.domain.model.backlog.ResponseIssueBacklog
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface BacklogApiService {
    @POST("/api/Issue/backlog")
    suspend fun createIssue(
        @Header("Authorization") accessToken: String,
        @Query("projectId") projectId: Int,
        @Body requestIssueBacklog: RequestIssueBacklog
    )

    @GET("/api/Issue/backlog")
    suspend fun getIssues(
        @Header("Authorization") accessToken: String,
        @Query("projectId") projectId: Int,
        @Query("pageSize") pageSize: Int,
        @Query("pageNumber") pageNumber: Int
    ): ResponseIssueBacklog

}