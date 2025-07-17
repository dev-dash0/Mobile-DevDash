package com.elfeky.domain.model.pin

import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.tenant.Tenant
import com.google.gson.annotations.SerializedName

data class PinnedItems(
    @SerializedName("Issues", alternate = ["issues"]) val issues: List<Issue>,
    @SerializedName("Projects", alternate = ["projects"]) val projects: List<Project>,
    @SerializedName("Sprints", alternate = ["sprints"]) val sprints: List<Sprint>,
    @SerializedName("Tenants", alternate = ["tenants"]) val tenants: List<Tenant>
)