package com.elfeky.domain.model.search

import com.google.gson.annotations.SerializedName

data class TenantSearch(
    @SerializedName("Description", alternate = ["description"]) val description: String,
    @SerializedName("Id", alternate = ["id"]) val id: Int,
    @SerializedName("Name", alternate = ["name"]) val name: String,
    @SerializedName("TenantCode", alternate = ["tenantCode"]) val tenantCode: String
)