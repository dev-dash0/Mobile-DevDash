package com.elfeky.domain.model.search

import com.google.gson.annotations.SerializedName

data class IssueSearch(
    @SerializedName("Priority", alternate = ["priority"]) val priority: String,
    @SerializedName("ProjectName", alternate = ["projectName"]) val projectName: String,
    @SerializedName("Status", alternate = ["status"]) val status: String,
    @SerializedName("TenantName", alternate = ["tenantName"]) val tenantName: String,
    @SerializedName("Title", alternate = ["title"]) val title: String
)