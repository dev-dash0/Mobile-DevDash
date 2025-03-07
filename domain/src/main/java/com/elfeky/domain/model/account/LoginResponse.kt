package com.elfeky.domain.model.account

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)