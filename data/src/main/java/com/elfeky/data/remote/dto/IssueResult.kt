package com.elfeky.data.remote.dto

import com.elfeky.domain.model.issue.Issue

data class IssueResult(
    val id: Int,
    val issue: Issue
)