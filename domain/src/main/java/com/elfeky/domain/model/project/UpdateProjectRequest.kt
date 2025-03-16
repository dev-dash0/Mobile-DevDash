package com.elfeky.domain.model.project

data class UpdateProjectRequest(
    val description: String,
    val endDate: String,
    val id: Int,
    val isPinned: Boolean,
    val name: String,
    val priority: String,
    val startDate: String,
    val status: String
)