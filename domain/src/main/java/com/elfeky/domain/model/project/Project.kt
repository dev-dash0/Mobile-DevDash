package com.elfeky.domain.model.project

import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.tenant.Tenant
import com.google.gson.annotations.SerializedName

data class Project(
    @SerializedName("CreationDate", alternate = ["creationDate"]) val creationDate: String,
    @SerializedName("Creator", alternate = ["creator"]) val creator: UserProfile,
    @SerializedName("CreatorId", alternate = ["creatorId"]) val creatorId: Int,
    @SerializedName("Description", alternate = ["description"]) val description: String,
    @SerializedName("EndDate", alternate = ["endDate"]) val endDate: String,
    @SerializedName("Id", alternate = ["id"]) val id: Int,
    @SerializedName("Name", alternate = ["name"]) val name: String,
    @SerializedName("Priority", alternate = ["priority"]) val priority: String,
    @SerializedName("ProjectCode", alternate = ["projectCode"]) val projectCode: String,
    @SerializedName("Role", alternate = ["role"]) val role: String?,
    @SerializedName("StartDate", alternate = ["startDate"]) val startDate: String,
    @SerializedName("Status", alternate = ["status"]) val status: String,
    @SerializedName("Tenant", alternate = ["tenant"]) val tenant: Tenant,
    @SerializedName("TenantId", alternate = ["tenantId"]) val tenantId: Int,
    @SerializedName(
        "UserProjects",
        alternate = ["userProjects"]
    ) val userProjects: List<UserProject>
)