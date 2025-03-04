package com.elfeky.domain.model

data class CalenderIssue(
    val title: String,
    val priority: String,
    val startDate: String,
    val deadline: String,
    val projectName: String,
    val tenantName: String
)
