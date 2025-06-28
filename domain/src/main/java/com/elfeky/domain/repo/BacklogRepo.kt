package com.elfeky.domain.repo

import com.elfeky.domain.model.issue.Issue
import java.io.File

interface BacklogRepo {
    suspend fun createIssue(
        projectId: Int,
        accessToken: String,
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
        projectId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Issue>

}