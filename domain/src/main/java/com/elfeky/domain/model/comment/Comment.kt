package com.elfeky.domain.model.comment

import com.elfeky.domain.model.account.UserProfile

data class Comment(
    val id: Int,
    val content: String,
    val createdBy: UserProfile,
    val createdById: Int,
    val creationDate: String,
    val issueId: Int,
    val projectId: Int,
    val sprintId: Int,
    val tenantId: Int
)