package com.elfeky.domain.model.account

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)
