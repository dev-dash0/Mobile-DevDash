package com.elfeky.domain.model.dashboard

data class CalenderIssue(
    val id: Int,
    val title: String,
    val priority: String,
    val type: String,
    val startDate: String,
    val deadline: String,
    val projectName: String,
    val tenantName: String
)
