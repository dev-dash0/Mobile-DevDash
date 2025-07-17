package com.elfeky.domain.model.dashboard

import com.google.gson.annotations.SerializedName

data class CalenderIssue(
    @SerializedName("Id", alternate = ["id"]) val id: Int,
    @SerializedName("Title", alternate = ["title"]) val title: String,
    @SerializedName("Priority", alternate = ["priority"]) val priority: String,
    @SerializedName("Type", alternate = ["type"]) val type: String,
    @SerializedName("StartDate", alternate = ["startDate"]) val startDate: String,
    @SerializedName("Deadline", alternate = ["deadline"]) val deadline: String,
    @SerializedName("ProjectName", alternate = ["projectName"]) val projectName: String,
    @SerializedName("TenantName", alternate = ["tenantName"]) val tenantName: String
)
