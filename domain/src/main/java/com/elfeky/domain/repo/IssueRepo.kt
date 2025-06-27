package com.elfeky.domain.repo

import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.issue.IssueFormFields

interface IssueRepo {
    suspend fun getIssue(accessToken: String, id: Int): Issue
    suspend fun updateIssue(
        accessToken: String,
        id: Int,
        formFields: IssueFormFields,
        attachmentFile: java.io.File?,
        attachmentMediaType: String?
    )

    suspend fun deleteIssue(accessToken: String, id: Int)
}