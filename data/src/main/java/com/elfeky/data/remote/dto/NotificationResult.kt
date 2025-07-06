package com.elfeky.data.remote.dto

import com.elfeky.domain.model.notification.Notification

data class NotificationResult(
    val notifications: List<Notification>,
    val userId: Int
)
