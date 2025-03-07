package com.elfeky.domain.model.project

data class Project(
    val creationDate: String,
    val creatorId: Int,
    val description: String,
    val endDate: String,
    val id: Int,
    val name: String,
    val priority: String,
    val projectCode: String,
    val startDate: String,
    val status: String,
    val tenantId: Int
)