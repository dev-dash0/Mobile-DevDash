package com.elfeky.domain.model.search

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("Issues", alternate = ["issues"]) val issues: List<IssueSearch>,
    @SerializedName("Projects", alternate = ["projects"]) val projects: List<ProjectSearch>,
    @SerializedName("Sprints", alternate = ["sprints"]) val sprints: List<Any>,
    @SerializedName("Tenants", alternate = ["tenants"]) val tenants: List<TenantSearch>
)