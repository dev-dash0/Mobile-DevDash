package com.elfeky.domain.model.tenant

import com.elfeky.domain.model.account.UserProfile
import com.google.gson.annotations.SerializedName

data class Tenant(
    @SerializedName("Description", alternate = ["description"]) val description: String,
    @SerializedName("Id", alternate = ["id"]) val id: Int,
    @SerializedName("Image", alternate = ["image"]) val image: String?,
    @SerializedName("JoinedUsers", alternate = ["joinedUsers"]) val joinedUsers: List<UserProfile>,
    @SerializedName("Keywords", alternate = ["keywords"]) val keywords: String,
    @SerializedName("Name", alternate = ["name"]) val name: String,
    @SerializedName("Owner", alternate = ["owner"]) val owner: UserProfile,
    @SerializedName("OwnerID", alternate = ["ownerID"]) val ownerID: Int,
    @SerializedName("Role", alternate = ["role"]) val role: Any?,
    @SerializedName("TenantCode", alternate = ["tenantCode"]) val tenantCode: String,
    @SerializedName("TenantUrl", alternate = ["tenantUrl"]) val tenantUrl: String
)