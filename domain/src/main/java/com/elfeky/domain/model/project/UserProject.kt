package com.elfeky.domain.model.project

import com.google.gson.annotations.SerializedName

data class UserProject(
    @SerializedName("JoinedDate", alternate = ["joinedDate"]) val joinedDate: String,
    @SerializedName("ProjectId", alternate = ["projectId"]) val projectId: Int,
    @SerializedName("Role", alternate = ["role"]) val role: String,
    @SerializedName("UserId", alternate = ["userId"]) val userId: Int
)