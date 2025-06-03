package com.elfeky.domain.model.backlog



data class Result(
    val assignedUsers: List<Any>,
    val attachment: Any,
    val attachmentPath: String,
    val createdBy: CreatedBy,
    val createdById: Int,
    val creationDate: String,
    val deadline: String,
    val deliveredDate: String,
    val description: String,
    val id: Int,
    val isBacklog: Boolean,
    val labels: String,
    val lastUpdate: String,
    val priority: String,
    val projectId: Int,
    val sprintId: Any,
    val startDate: String,
    val status: String,
    val tenantId: Int,
    val title: String,
    val type: String
)