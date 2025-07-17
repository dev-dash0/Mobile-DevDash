package com.elfeky.domain.model.issue

import com.elfeky.domain.model.account.UserProfile
import com.google.gson.annotations.SerializedName

data class Issue(
    @SerializedName(
        "AssignedUsers",
        alternate = ["assignedUsers"]
    ) val assignedUsers: List<UserProfile>,
    @SerializedName("Attachment", alternate = ["attachment"]) val attachment: String?,
    @SerializedName("AttachmentPath", alternate = ["attachmentPath"]) val attachmentPath: String?,
    @SerializedName("CreatedBy", alternate = ["createdBy"]) val createdBy: UserProfile,
    @SerializedName("CreatedById", alternate = ["createdById"]) val createdById: Int,
    @SerializedName("CreationDate", alternate = ["creationDate"]) val creationDate: String,
    @SerializedName("Deadline", alternate = ["deadline"]) val deadline: String?,
    @SerializedName("DeliveredDate", alternate = ["deliveredDate"]) val deliveredDate: String?,
    @SerializedName("Description", alternate = ["description"]) val description: String?,
    @SerializedName("Id", alternate = ["id"]) val id: Int,
    @SerializedName("IsBacklog", alternate = ["isBacklog"]) val isBacklog: Boolean,
    @SerializedName("Labels", alternate = ["labels"]) val labels: String?,
    @SerializedName("LastUpdate", alternate = ["lastUpdate"]) val lastUpdate: String?,
    @SerializedName("Priority", alternate = ["priority"]) val priority: String,
    @SerializedName("ProjectId", alternate = ["projectId"]) val projectId: Int,
    @SerializedName("SprintId", alternate = ["sprintId"]) val sprintId: Int?,
    @SerializedName("StartDate", alternate = ["startDate"]) val startDate: String?,
    @SerializedName("Status", alternate = ["status"]) val status: String,
    @SerializedName("TenantId", alternate = ["tenantId"]) val tenantId: Int,
    @SerializedName("Title", alternate = ["title"]) val title: String,
    @SerializedName("Type", alternate = ["type"]) val type: String
)