package com.elfeky.domain.model.search

data class IssueSearch(
    val priority: String,
    val projectName: String,
    val status: String,
    val tenantName: String,
    val title: String
)