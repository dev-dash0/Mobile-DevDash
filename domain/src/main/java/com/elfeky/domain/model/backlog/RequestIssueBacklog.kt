package com.elfeky.domain.model.backlog

data class RequestIssueBacklog(
    val title: String,
    val priority: String,
    val startDate: String,
    val deadline: String,
    val isBacklog: Boolean,
    val labels: String,
    val status: String,
    val deliveredDate: String,
    val type: String,
    val description: String
)
