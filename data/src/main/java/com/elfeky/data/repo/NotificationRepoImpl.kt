package com.elfeky.data.repo

import com.elfeky.data.remote.NotificationApiService
import com.elfeky.domain.model.notification.Notification
import com.elfeky.domain.repo.NotificationRepo
import javax.inject.Inject

class NotificationRepoImpl @Inject constructor(
    private val apiService: NotificationApiService
) : NotificationRepo {
    override suspend fun getNotifications(accessToken: String): List<Notification> =
        apiService.getNotifications("Bearer $accessToken").result.notifications

    override suspend fun markNotificationRead(
        accessToken: String,
        notificationId: Int
    ) {
        apiService.markNotificationRead("Bearer $accessToken", notificationId)
    }
}