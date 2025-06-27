package com.elfeky.domain.repo

import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.issue.IssueFormFields

interface BacklogRepo {
    suspend fun createIssue(
        accessToken: String,
        projectId: Int,
        formFields: IssueFormFields,
        attachmentFile: java.io.File?,
        attachmentMediaType: String?
    )

    suspend fun getIssues(
        accessToken: String,
        projectId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Issue>

}