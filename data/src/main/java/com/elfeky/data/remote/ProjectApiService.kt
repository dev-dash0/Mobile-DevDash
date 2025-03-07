package com.elfeky.data.remote

import com.elfeky.data.dto.CRUDResponse
import com.elfeky.data.dto.ProjectResult
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProjectApiService {
    @POST("/api/Project")
    suspend fun createProject(
        @Header("Authorization") accessToken: String,
        @Body projectRequest: ProjectRequest
    ): CRUDResponse<ProjectResult>

    @GET("/api/Project")
    suspend fun getProjects(@Header("Authorization") accessToken: String): CRUDResponse<List<Project>>

    @GET("/api/Project/{projectId}")
    suspend fun getProjectById(
        @Header("Authorization") accessToken: String,
        @Path("projectId") id: Int
    ): CRUDResponse<Project>

    @PUT("/api/Project/{projectId}")
    suspend fun updateProject(
        @Header("Authorization") accessToken: String,
        @Path("projectId") id: Int,
        @Body projectRequest: ProjectRequest
    ): CRUDResponse<Unit>

    @DELETE("/api/Project/{projectId}")
    suspend fun deleteProject(
        @Header("Authorization") accessToken: String,
        @Path("projectId") id: Int
    ): CRUDResponse<Unit>
}