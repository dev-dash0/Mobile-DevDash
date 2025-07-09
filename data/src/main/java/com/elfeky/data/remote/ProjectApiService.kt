package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ProjectResult
import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.model.project.UpdateProjectRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectApiService {
    @POST("/api/Project")
    suspend fun createProject(
        @Header("Authorization") accessToken: String,
        @Query("tenantId") id: Int,
        @Body projectRequest: ProjectRequest
    ): ServiceResponse<ProjectResult>

    @GET("/api/Project")
    suspend fun getTenantProjects(
        @Header("Authorization") accessToken: String,
        @Query("tenantId") id: Int
    ): ServiceResponse<List<Project>>

    @GET("/api/Project/{projectId}")
    suspend fun getProjectById(
        @Header("Authorization") accessToken: String,
        @Path("projectId") id: Int
    ): ServiceResponse<Project>

    @PUT("/api/Project/{projectId}")
    suspend fun updateProject(
        @Header("Authorization") accessToken: String,
        @Path("projectId") id: Int,
        @Body projectRequest: UpdateProjectRequest
    ): ServiceResponse<Unit>

    @DELETE("/api/Project/{projectId}")
    suspend fun deleteProject(
        @Header("Authorization") accessToken: String,
        @Path("projectId") id: Int
    ): ServiceResponse<Unit>

    @GET("/api/DashBoard/allproject")
    suspend fun getUserProjects(
        @Header("Authorization") accessToken: String
    ): ServiceResponse<List<Project>>
}