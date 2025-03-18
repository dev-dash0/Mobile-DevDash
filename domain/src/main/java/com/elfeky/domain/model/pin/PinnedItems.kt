package com.elfeky.domain.model.pin

import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.tenant.Tenant

data class PinnedItems(
    val issues: List<Issue>,
    val projects: List<Project>,
    val sprints: List<Sprint>,
    val tenants: List<Tenant>
)