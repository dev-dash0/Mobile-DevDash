package com.elfeky.domain.model.sprint

import com.elfeky.domain.model.account.User
import com.elfeky.domain.model.issue.Issue

data class Sprint(
    val createdAt: String,
    val createdBy: User,
    val createdById: Int,
    val description: String,
    val endDate: String,
    val id: Int,
    val issues: List<Issue>,
    val projectId: Int,
    val startDate: String,
    val status: String,
    val summary: String,
    val tenantId: Int,
    val title: String
)