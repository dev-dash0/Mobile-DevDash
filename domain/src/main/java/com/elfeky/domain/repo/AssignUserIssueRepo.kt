package com.elfeky.domain.repo

import com.elfeky.domain.model.issue.AssignResponse
import com.elfeky.domain.model.issue.AssignUserIssue

interface AssignUserIssueRepo {
    suspend fun assignUserIssue(
        accessToken: String,
        assignUserIssue: AssignUserIssue
    ): AssignResponse

    suspend fun unassignUserIssue(
        accessToken: String,
        assignUserIssue: AssignUserIssue
    )
}