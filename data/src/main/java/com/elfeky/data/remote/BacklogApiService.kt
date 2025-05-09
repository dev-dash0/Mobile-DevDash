package com.elfeky.data.remote

import com.elfeky.domain.model.backlog.Issue
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface BacklogApiService {
    @POST("/api/Issue/backlog")
    suspend fun createIssue(
        @Header("Authorization") accessToken: String,
        @Query("projectId") projectId: Int,
        @Body issue: Issue
    )
}