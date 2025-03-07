package com.elfeky.data.repo

import com.elfeky.data.remote.ProjectApiService
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.repo.ProjectRepo

class ProjectRepoImpl(private val projectApiService: ProjectApiService) : ProjectRepo {
    override suspend fun createProject(accessToken: String, request: ProjectRequest): Project =
        projectApiService.createProject("Bearer $accessToken", request).result.project

    override suspend fun getAllProjects(accessToken: String, id: Int): List<Project> =
        projectApiService.getAllProjects("Bearer $accessToken", id).result

    override suspend fun getProjectById(accessToken: String, id: Int): Project =
        projectApiService.getProjectById("Bearer $accessToken", id).result

    override suspend fun updateProject(accessToken: String, id: Int, request: ProjectRequest) =
        projectApiService.updateProject("Bearer $accessToken", id, request).result

    override suspend fun deleteProject(accessToken: String, id: Int) =
        projectApiService.deleteProject("Bearer $accessToken", id).result
}