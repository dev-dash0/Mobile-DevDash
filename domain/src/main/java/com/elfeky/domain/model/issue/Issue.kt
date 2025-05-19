package com.elfeky.domain.model.issue

import com.elfeky.domain.model.account.UserProfile

data class Issue(
    val assignedUsers: List<UserProfile>,
    val createdBy: Int,
    val createdById: Int,
    val creationDate: String,
    val deadline: String,
    val deliveredDate: String,
    val description: String,
    val id: Int,
    val isBacklog: Boolean,
    val lastUpdate: String,
    val priority: String,
    val projectId: Int,
    val sprintId: Int,
    val startDate: String,
    val status: String,
    val tenantId: Int,
    val title: String,
    val type: String
)