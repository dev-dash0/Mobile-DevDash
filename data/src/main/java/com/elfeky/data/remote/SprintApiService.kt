package com.elfeky.data.remote

import com.elfeky.domain.model.sprint.RequestSprint
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SprintApiService {
    @POST("/api/Sprint")
    suspend fun createSprint(
        @Header("Authorization") accessToken: String,
        @Query("projectId") projectId: Int,
        @Body request: RequestSprint
    )
}