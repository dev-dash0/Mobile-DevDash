package com.elfeky.domain.repo

import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.pin.PinnedItems
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.tenant.Tenant

interface PinRepo {
    suspend fun pinTenant(accessToken: String, id: Int)
    suspend fun pinProject(accessToken: String, id: Int)
    suspend fun pinSprint(accessToken: String, id: Int)
    suspend fun pinIssue(accessToken: String, id: Int)
    suspend fun getPinnedTenants(accessToken: String): List<Tenant>
    suspend fun getPinnedProjects(accessToken: String): List<Project>
    suspend fun getPinnedSprints(accessToken: String): List<Sprint>
    suspend fun getPinnedIssues(accessToken: String): List<Issue>
    suspend fun unpinTenant(accessToken: String, id: Int)
    suspend fun unpinProject(accessToken: String, id: Int)
    suspend fun unpinSprint(accessToken: String, id: Int)
    suspend fun unpinIssue(accessToken: String, id: Int)
    suspend fun getAllPinnedItems(accessToken: String): PinnedItems
}
