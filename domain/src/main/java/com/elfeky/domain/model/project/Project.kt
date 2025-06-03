package com.elfeky.domain.model.project

import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.tenant.Tenant

data class Project(
    val creationDate: String,
    val creator: UserProfile,
    val creatorId: Int,
    val description: String,
    val endDate: String,
    val id: Int,
    val name: String,
    val priority: String,
    val projectCode: String,
    val role: String?,
    val startDate: String,
    val status: String,
    val tenant: Tenant,
    val tenantId: Int,
    val userProjects: List<UserProject>
)