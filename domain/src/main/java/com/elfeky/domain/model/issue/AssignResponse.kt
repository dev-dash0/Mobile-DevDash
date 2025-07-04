package com.elfeky.domain.model.issue

import com.google.gson.annotations.SerializedName

data class AssignResponse(
    val issueId: Int,
    val userId: Int,
    @SerializedName("assign_date") val assignDate: String
)