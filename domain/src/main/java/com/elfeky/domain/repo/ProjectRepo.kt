package com.elfeky.domain.repo

import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest

interface ProjectRepo {
    suspend fun createProject(accessToken: String, request: ProjectRequest): Project
    suspend fun getAllProjects(accessToken: String, id: Int): List<Project>
    suspend fun getProjectById(accessToken: String, id: Int): Project
    suspend fun updateProject(accessToken: String, id: Int, request: ProjectRequest)
    suspend fun deleteProject(accessToken: String, id: Int)
}