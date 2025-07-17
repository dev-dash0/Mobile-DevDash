package com.elfeky.domain.model.comment

import com.elfeky.domain.model.account.UserProfile
import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("Id", alternate = ["id"]) val id: Int,
    @SerializedName("Content", alternate = ["content"]) val content: String,
    @SerializedName("CreatedBy", alternate = ["createdBy"]) val createdBy: UserProfile,
    @SerializedName("CreatedById", alternate = ["createdById"]) val createdById: Int,
    @SerializedName("CreationDate", alternate = ["creationDate"]) val creationDate: String,
    @SerializedName("IssueId", alternate = ["issueId"]) val issueId: Int,
    @SerializedName("ProjectId", alternate = ["projectId"]) val projectId: Int,
    @SerializedName("SprintId", alternate = ["sprintId"]) val sprintId: Int,
    @SerializedName("TenantId", alternate = ["tenantId"]) val tenantId: Int
)