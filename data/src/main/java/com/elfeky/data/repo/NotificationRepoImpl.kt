package com.elfeky.data.repo

import com.elfeky.data.remote.NotificationApiService
import com.elfeky.domain.model.notification.Notification
import com.elfeky.domain.repo.NotificationRepo
import javax.inject.Inject

class NotificationRepoImpl @Inject constructor(
    private val apiService: NotificationApiService
) : NotificationRepo {
    override suspend fun getNotifications(accessToken: String): List<Notification> =
        apiService.getNotifications(accessToken).result

    override suspend fun markNotificationRead(
        accessToken: String,
        notificationId: Int
    ) {
        apiService.markNotificationRead(accessToken, notificationId)
    }
}