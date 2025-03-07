package com.elfeky.domain.model.project

data class ProjectRequest(
    val creationDate: String,
    val description: String,
    val endDate: String,
    val name: String,
    val priority: String,
    val startDate: String,
    val status: String
)