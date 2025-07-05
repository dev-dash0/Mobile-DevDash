package com.elfeky.data.remote

import com.elfeky.data.remote.dto.NotificationResult
import com.elfeky.data.remote.dto.ServiceResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationApiService {
    @GET("/api/Notification")
    suspend fun getNotifications(
        @Header("Authorization") accessToken: String
    ): ServiceResponse<NotificationResult>

    @POST("/api/Notification/{notificationId}/mark-as-read")
    suspend fun markNotificationRead(
        @Header("Authorization") accessToken: String,
        @Path("notificationId") notificationId: Int
    ): ServiceResponse<Nothing>
}