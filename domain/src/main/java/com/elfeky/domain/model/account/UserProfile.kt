package com.elfeky.domain.model.account

import com.google.gson.annotations.SerializedName

data class UserProfile(
    val id: Int,
    val imageUrl: String?,
    val email: String,
    val firstName: String,
    val lastName: String,
    val userName: String,
    val phoneNumber: String,
    val birthday: String,
    @SerializedName("personaltenantId") val personalTenantId: Int,
)