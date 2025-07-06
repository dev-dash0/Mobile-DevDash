package com.elfeky.domain.model.search

data class ProjectSearch(
    val description: String,
    val id: Int,
    val name: String,
    val priority: String,
    val projectCode: String,
    val tenantName: String
)