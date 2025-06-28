package com.elfeky.domain.repo

import com.elfeky.domain.model.issue.Issue
import java.io.File

interface SprintIssueRepo {
    suspend fun createIssue(
        accessToken: String,
        sprintId: Int,
        priority: String,
        status: String,
        title: String,
        type: String,
        description: String,
        isBacklog: Boolean,
        startDate: String,
        deadline: String,
        deliveredDate: String,
        lastUpdate: String,
        labels: String,
        attachmentFile: File?,
        attachmentMediaType: String?
    ): Issue

    suspend fun getIssues(
        accessToken: String,
        sprintId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Issue>
}