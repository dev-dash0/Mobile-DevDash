package com.elfeky.domain.model.account

data class LoginRequest(
    val email: String,
    val password: String
)