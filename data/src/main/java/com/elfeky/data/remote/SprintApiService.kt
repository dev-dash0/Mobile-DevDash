package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.sprint.SprintRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SprintApiService {
    @POST("/api/Sprint")
    suspend fun createSprint(
        @Header("Authorization") accessToken: String,
        @Query("projectId") projectId: Int,
        @Body request: SprintRequest
    )

    @GET("/api/Sprint")
    suspend fun getProjectSprints(
        @Header("Authorization") accessToken: String,
        @Query("projectId") projectId: Int,
        @Query("pageSize") pageSize: Int,
        @Query("pageNumber") pageNumber: Int
    ): ServiceResponse<List<Sprint>>

    @GET("/api/Sprint/{sprintId}")
    suspend fun getSprintById(
        @Header("Authorization") accessToken: String,
        @Path("sprintId") id: Int
    ): ServiceResponse<Sprint>

    @PUT("/api/Sprint/{sprintId}")
    suspend fun updateSprint(
        @Header("Authorization") accessToken: String,
        @Path("sprintId") id: Int,
        @Body request: SprintRequest
    )

    @DELETE("/api/Sprint/{sprintId}")
    suspend fun deleteSprint(
        @Header("Authorization") accessToken: String,
        @Path("sprintId") id: Int
    )
}