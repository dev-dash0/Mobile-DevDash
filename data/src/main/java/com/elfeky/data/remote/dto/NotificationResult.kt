package com.elfeky.data.remote.dto

import com.elfeky.domain.model.notification.Notification
import com.google.gson.annotations.SerializedName

data class NotificationResult(
    @SerializedName("UserId", alternate = ["userId"]) val userId: Int,
    @SerializedName(
        "Notifications",
        alternate = ["notifications"]
    ) val notifications: List<Notification>
)
