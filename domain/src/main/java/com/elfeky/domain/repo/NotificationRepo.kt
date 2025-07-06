package com.elfeky.domain.repo

import com.elfeky.domain.model.notification.Notification

interface NotificationRepo {
    suspend fun getNotifications(accessToken: String): List<Notification>
    suspend fun markNotificationRead(accessToken: String, notificationId: Int)
}