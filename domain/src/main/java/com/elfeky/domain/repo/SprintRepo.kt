package com.elfeky.domain.repo

import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.sprint.SprintRequest

interface SprintRepo {
    suspend fun createSprint(accessToken: String, projectId: Int, request: SprintRequest)
    suspend fun getSprintById(accessToken: String, id: Int): Sprint
    suspend fun updateSprint(accessToken: String, id: Int, request: SprintRequest)
    suspend fun deleteSprint(accessToken: String, id: Int)
    suspend fun getProjectSprints(
        accessToken: String,
        projectId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Sprint>
}