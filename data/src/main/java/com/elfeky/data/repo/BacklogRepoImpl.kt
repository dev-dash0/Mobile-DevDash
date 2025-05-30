package com.elfeky.data.repo

import com.elfeky.data.remote.BacklogApiService
import com.elfeky.domain.model.backlog.RequestIssueBacklog
import com.elfeky.domain.model.backlog.ResponseIssueBacklog
import com.elfeky.domain.repo.BacklogRepo
import javax.inject.Inject

class BacklogRepoImpl @Inject constructor(
    private val backlogApiService: BacklogApiService
): BacklogRepo {
    override suspend fun createIssue(
        accessToken: String,
        projectId: Int,
        requestIssueBacklog: RequestIssueBacklog
    ) {
        backlogApiService.createIssue(accessToken = "Bearer $accessToken", projectId = projectId, requestIssueBacklog = requestIssueBacklog)
    }

    override suspend fun getIssues(
        accessToken: String,
        projectId: Int,
        pageSize: Int,
        pageNumber: Int
    ): ResponseIssueBacklog {
        return backlogApiService.getIssues(accessToken = "Bearer $accessToken", projectId = projectId, pageSize = pageSize, pageNumber = pageNumber)
    }
}