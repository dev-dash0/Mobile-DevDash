package com.elfeky.domain.model.notification

import com.elfeky.domain.model.issue.Issue
import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("CreatedAt", alternate = ["createdAt"]) val createdAt: String,
    @SerializedName("Id", alternate = ["id"]) val id: Int,
    @SerializedName("IsRead", alternate = ["isRead"]) val isRead: Boolean,
    @SerializedName("Issue", alternate = ["issue"]) val issue: Issue?,
    @SerializedName("IssueId", alternate = ["issueId"]) val issueId: Int?,
    @SerializedName("Message", alternate = ["message"]) val message: String,
    @SerializedName("UserId", alternate = ["userId"]) val userId: Int
)