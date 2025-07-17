package com.elfeky.domain.model.sprint

import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.issue.Issue
import com.google.gson.annotations.SerializedName

data class Sprint(
    @SerializedName("CreatedAt", alternate = ["createdAt"]) val createdAt: String,
    @SerializedName("CreatedBy", alternate = ["createdBy"]) val createdBy: UserProfile,
    @SerializedName("CreatedById", alternate = ["createdById"]) val createdById: Int,
    @SerializedName("Description", alternate = ["description"]) val description: String,
    @SerializedName("EndDate", alternate = ["endDate"]) val endDate: String,
    @SerializedName("Id", alternate = ["id"]) val id: Int,
    @SerializedName("Issues", alternate = ["issues"]) val issues: List<Issue>,
    @SerializedName("ProjectId", alternate = ["projectId"]) val projectId: Int,
    @SerializedName("StartDate", alternate = ["startDate"]) val startDate: String,
    @SerializedName("Status", alternate = ["status"]) val status: String,
    @SerializedName("Summary", alternate = ["summary"]) val summary: String,
    @SerializedName("TenantId", alternate = ["tenantId"]) val tenantId: Int,
    @SerializedName("Title", alternate = ["title"]) val title: String
)