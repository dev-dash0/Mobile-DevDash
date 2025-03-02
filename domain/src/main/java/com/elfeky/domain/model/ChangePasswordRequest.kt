package com.elfeky.domain.model

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)
