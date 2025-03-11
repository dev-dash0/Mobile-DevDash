package com.elfeky.domain.model.account

data class UserProfileRequest(
    val firstName: String?,
    val lastName: String?,
    val userName: String?,
    val imageUrl: String?,
    val phoneNumber: String?,
    val birthday: String?
)
