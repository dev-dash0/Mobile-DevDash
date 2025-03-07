package com.elfeky.data.repo

import com.elfeky.data.remote.ProjectApiService
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.repo.ProjectRepo

class ProjectRepoImpl(private val projectApiService: ProjectApiService) : ProjectRepo {
    override suspend fun createProject(accessToken: String, request: ProjectRequest): Project =
        projectApiService.createProject(accessToken, request).result.project

    override suspend fun getProjects(accessToken: String): List<Project> =
        projectApiService.getProjects(accessToken).result

    override suspend fun getProjectById(accessToken: String, id: Int): Project =
        projectApiService.getProjectById(accessToken, id).result

    override suspend fun updateProject(accessToken: String, id: Int, request: ProjectRequest) =
        projectApiService.updateProject(accessToken, id, request).result

    override suspend fun deleteProject(accessToken: String, id: Int) =
        projectApiService.deleteProject(accessToken, id).result
}