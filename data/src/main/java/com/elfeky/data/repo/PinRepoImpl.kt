package com.elfeky.data.repo

import com.elfeky.data.remote.PinApiService
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.pin.PinnedItems
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.tenant.Tenant
import com.elfeky.domain.repo.PinRepo
import javax.inject.Inject

class PinRepoImpl @Inject constructor(
    private val pinApiService: PinApiService
) : PinRepo {
    override suspend fun pinTenant(accessToken: String, id: Int) =
        pinApiService.pinTenant("Bearer $accessToken", id)

    override suspend fun pinProject(accessToken: String, id: Int) =
        pinApiService.pinProject("Bearer $accessToken", id)

    override suspend fun pinSprint(accessToken: String, id: Int) =
        pinApiService.pinSprint("Bearer $accessToken", id)

    override suspend fun pinIssue(accessToken: String, id: Int) =
        pinApiService.pinIssue("Bearer $accessToken", id)

    override suspend fun getAllPinnedItems(accessToken: String): PinnedItems =
        pinApiService.getAllPinnedItems("Bearer $accessToken").result

    override suspend fun getPinnedTenants(accessToken: String): List<Tenant> =
        pinApiService.getPinnedTenants("Bearer $accessToken").result

    override suspend fun getPinnedProjects(accessToken: String): List<Project> =
        pinApiService.getPinnedProjects("Bearer $accessToken").result

    override suspend fun getPinnedSprints(accessToken: String): List<Sprint> =
        pinApiService.getPinnedSprints("Bearer $accessToken").result

    override suspend fun getPinnedIssues(accessToken: String): List<Issue> =
        pinApiService.getPinnedIssues("Bearer $accessToken").result

    override suspend fun unpinTenant(accessToken: String, id: Int) =
        pinApiService.unpinTenant("Bearer $accessToken", id)

    override suspend fun unpinProject(accessToken: String, id: Int) =
        pinApiService.unpinProject("Bearer $accessToken", id)

    override suspend fun unpinSprint(accessToken: String, id: Int) =
        pinApiService.unpinSprint("Bearer $accessToken", id)

    override suspend fun unpinIssue(accessToken: String, id: Int) =
        pinApiService.unpinIssue("Bearer $accessToken", id)
}