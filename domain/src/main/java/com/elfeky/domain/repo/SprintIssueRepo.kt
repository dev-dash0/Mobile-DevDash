package com.elfeky.domain.repo

import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.issue.IssueFormFields
import java.io.File

interface SprintIssueRepo {
    suspend fun createIssue(
        accessToken: String,
        sprintId: Int,
        formFields: IssueFormFields,
        attachmentFile: File?,
        attachmentMediaType: String?
    )

    suspend fun getIssues(
        accessToken: String,
        sprintId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Issue>
}