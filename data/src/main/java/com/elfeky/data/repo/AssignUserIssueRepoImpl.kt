package com.elfeky.data.repo

import com.elfeky.data.remote.AssignUserIssueApiService
import com.elfeky.domain.model.issue.AssignResponse
import com.elfeky.domain.model.issue.AssignUserIssue
import com.elfeky.domain.repo.AssignUserIssueRepo
import javax.inject.Inject

class AssignUserIssueRepoImpl @Inject constructor(
    private val apiService: AssignUserIssueApiService
) : AssignUserIssueRepo {
    override suspend fun assignUserIssue(
        accessToken: String,
        assignUserIssue: AssignUserIssue
    ): AssignResponse = apiService.assignUserIssue("Bearer $accessToken", assignUserIssue).result

    override suspend fun unassignUserIssue(
        accessToken: String,
        assignUserIssue: AssignUserIssue
    ) {
        apiService.unassignUserIssue("Bearer $accessToken", assignUserIssue)
    }
}