package com.elfeky.domain.model.project

data class Project(
    val id: Int,
    val name: String,
    val description: String,
    val projectCode: String,
    val startDate: String,
    val endDate: String,
    val creationDate: String,
    val priority: String,
    val status: String,
    val tenantId: Int,
    val creatorId: Int,
)