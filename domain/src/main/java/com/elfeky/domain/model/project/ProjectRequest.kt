package com.elfeky.domain.model.project

data class ProjectRequest(
    val name: String,
    val description: String,
    val creationDate: String,
    val startDate: String?,
    val endDate: String?,
    val priority: String,
    val status: String
)