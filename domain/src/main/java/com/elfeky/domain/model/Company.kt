package com.elfeky.domain.model

data class Company(
    val description: String,
    val id: Int,
    val image: String,
    val joinedUsers: List<User>,
    val keywords: String,
    val name: String,
    val owner: User,
    val ownerID: Int,
    val tenantCode: String,
    val tenantUrl: String
)