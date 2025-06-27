package com.elfeky.domain.model.sprint

data class SprintRequest(
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val status: String,
    val summary: String
)
