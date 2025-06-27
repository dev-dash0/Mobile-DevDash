package com.elfeky.domain.model.issue

data class IssueFormFields(
    val priority: String,
    val status: String,
    val title: String,
    val type: String,
    val labels: String?,
    val description: String?,
    val isBacklog: Boolean,
    val startDate: String?,
    val deadline: String?,
    val deliveredDate: String?,
    val lastUpdate: String?
)