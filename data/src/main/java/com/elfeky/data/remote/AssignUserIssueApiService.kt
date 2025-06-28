package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.issue.AssignResponse
import com.elfeky.domain.model.issue.AssignUserIssue
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface AssignUserIssueApiService {
    @POST("/api/IssueAssignedUser")
    suspend fun assignUserIssue(
        @Header("Authorization") accessToken: String,
        @Body assignUserIssue: AssignUserIssue,
    ): ServiceResponse<AssignResponse>

    @DELETE("/api/IssueAssignedUser")
    suspend fun unassignUserIssue(
        @Header("Authorization") accessToken: String,
        @Body assignUserIssue: AssignUserIssue,
    ): ServiceResponse<Nothing>
}