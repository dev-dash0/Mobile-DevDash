package com.elfeky.domain.repo

import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.model.project.UpdateProjectRequest

interface ProjectRepo {
    suspend fun createProject(accessToken: String, request: ProjectRequest, tenantId: Int): Project
    suspend fun getAllProjects(accessToken: String, tenantId: Int): List<Project>
    suspend fun getProjectById(accessToken: String, id: Int): Project
    suspend fun updateProject(accessToken: String, id: Int, request: UpdateProjectRequest)
    suspend fun deleteProject(accessToken: String, id: Int)
}