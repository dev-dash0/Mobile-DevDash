package com.elfeky.domain.model.tenant

import com.elfeky.domain.model.account.UserProfile

data class Tenant(
    val description: String,
    val id: Int,
    val image: String?,
    val joinedUsers: List<UserProfile>,
    val keywords: String,
    val name: String,
    val owner: UserProfile,
    val ownerID: Int,
    val role: Any?,
    val tenantCode: String,
    val tenantUrl: String
)