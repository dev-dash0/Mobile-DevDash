package com.elfeky.domain.model

data class UserProfile(
    val id: Int,
    val imageUrl: String?,
    val email: String,
    val firstName: String,
    val lastName: String,
    val userName: String,
)