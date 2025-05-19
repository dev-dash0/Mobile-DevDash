package com.elfeky.data.repo

import com.elfeky.data.remote.ProjectApiService
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.model.project.UpdateProjectRequest
import com.elfeky.domain.repo.ProjectRepo
import javax.inject.Inject

class ProjectRepoImpl @Inject constructor(
    private val projectApiService: ProjectApiService
) :
    ProjectRepo {
    override suspend fun createProject(
        accessToken: String,
        request: ProjectRequest,
        tenantId: Int
    ): Project =
        projectApiService.createProject("Bearer $accessToken", tenantId, request).result.project

    override suspend fun getTenantProjects(accessToken: String, tenantId: Int): List<Project> =
        projectApiService.getTenantProjects("Bearer $accessToken", tenantId).result

    override suspend fun getProjectById(accessToken: String, id: Int): Project =
        projectApiService.getProjectById("Bearer $accessToken", id).result

    override suspend fun updateProject(
        accessToken: String,
        id: Int,
        request: UpdateProjectRequest
    ) =
        projectApiService.updateProject("Bearer $accessToken", id, request).result

    override suspend fun deleteProject(accessToken: String, id: Int) =
        projectApiService.deleteProject("Bearer $accessToken", id).result
}