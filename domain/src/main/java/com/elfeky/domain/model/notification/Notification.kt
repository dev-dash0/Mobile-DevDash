package com.elfeky.domain.model.notification

import com.elfeky.domain.model.issue.Issue

data class Notification(
    val createdAt: String,
    val id: Int,
    val isRead: Boolean,
    val issue: Issue?,
    val issueId: Int?,
    val message: String,
    val userId: Int
)