package com.elfeky.domain.model.tenant

import com.elfeky.domain.model.account.User

data class Tenant(
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