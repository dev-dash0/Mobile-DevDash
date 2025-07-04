package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.notification.Notification
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationApiService {
    @GET("/api/Notification")
    suspend fun getNotifications(
        @Header("Authorization") accessToken: String
    ): ServiceResponse<List<Notification>>

    @POST("/api/Notification/{notificationId}/mark-as-read")
    suspend fun markNotificationRead(
        @Header("Authorization") accessToken: String,
        @Path("notificationId") notificationId: Int
    ): ServiceResponse<Nothing>
}