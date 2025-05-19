package com.elfeky.data.repo

import com.elfeky.data.remote.BacklogApiService
import com.elfeky.domain.model.backlog.Issue
import com.elfeky.domain.repo.BacklogRepo
import javax.inject.Inject

class BacklogRepoImpl @Inject constructor(
    private val backlogApiService: BacklogApiService
): BacklogRepo {
    override suspend fun createIssue(
        accessToken: String,
        projectId: Int,
        issue: Issue
    ) {
        backlogApiService.createIssue(accessToken = "Bearer $accessToken", projectId = projectId, issue = issue)
    }
}