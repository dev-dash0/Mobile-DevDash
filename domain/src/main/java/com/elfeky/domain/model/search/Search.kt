package com.elfeky.domain.model.search

data class Search(
    val issues: List<IssueSearch>,
    val projects: List<ProjectSearch>,
    val sprints: List<Any>,
    val tenants: List<TenantSearch>
)