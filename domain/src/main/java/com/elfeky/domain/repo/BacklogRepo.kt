package com.elfeky.domain.repo

import com.elfeky.domain.model.backlog.RequestIssueBacklog
import com.elfeky.domain.model.backlog.ResponseIssueBacklog

interface BacklogRepo {
    suspend fun createIssue(accessToken: String, projectId: Int, requestIssueBacklog: RequestIssueBacklog)
    suspend fun getIssues(accessToken: String, projectId: Int, pageSize: Int, pageNumber: Int): ResponseIssueBacklog

}