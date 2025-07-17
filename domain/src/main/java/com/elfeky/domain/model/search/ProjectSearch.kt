package com.elfeky.domain.model.search

import com.google.gson.annotations.SerializedName

data class ProjectSearch(
    @SerializedName("Description", alternate = ["description"]) val description: String,
    @SerializedName("Id", alternate = ["id"]) val id: Int,
    @SerializedName("Name", alternate = ["name"]) val name: String,
    @SerializedName("Priority", alternate = ["priority"]) val priority: String,
    @SerializedName("ProjectCode", alternate = ["projectCode"]) val projectCode: String,
    @SerializedName("TenantName", alternate = ["tenantName"]) val tenantName: String
)