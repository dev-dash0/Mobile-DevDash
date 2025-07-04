package com.elfeky.domain.model.notification

data class Notification(
    val createdAt: String,
    val id: Int,
    val isRead: Boolean,
    val issue: Any,
    val issueId: Any,
    val message: String,
    val userId: Int
)