package com.elfeky.domain.repo

import com.elfeky.domain.model.backlog.Issue

interface BacklogRepo {
    suspend fun createIssue(accessToken: String,projectId: Int, issue: Issue)
}