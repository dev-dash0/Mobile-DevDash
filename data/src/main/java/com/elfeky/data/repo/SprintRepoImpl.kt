package com.elfeky.data.repo

import com.elfeky.data.remote.SprintApiService
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.sprint.SprintRequest
import com.elfeky.domain.repo.SprintRepo
import javax.inject.Inject

class SprintRepoImpl @Inject constructor(
    private val sprintApiService: SprintApiService
) : SprintRepo {
    override suspend fun createSprint(
        accessToken: String,
        projectId: Int,
        request: SprintRequest
    ) {
        sprintApiService.createSprint(
            accessToken = "Bearer $accessToken",
            projectId = projectId,
            request = request
        )
    }

    override suspend fun getProjectSprints(
        accessToken: String,
        projectId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Sprint> {
        return sprintApiService.getProjectSprints(
            accessToken = "Bearer $accessToken",
            projectId = projectId,
            pageSize = pageSize,
            pageNumber = pageNumber
        ).result
    }

    override suspend fun getSprintById(
        accessToken: String,
        id: Int
    ): Sprint {
        return sprintApiService.getSprintById("Bearer $accessToken", id).result
    }

    override suspend fun updateSprint(
        accessToken: String,
        id: Int,
        request: SprintRequest
    ) {
        sprintApiService.updateSprint("Bearer $accessToken", id, request)
    }

    override suspend fun deleteSprint(accessToken: String, id: Int) {
        sprintApiService.deleteSprint("Bearer $accessToken", id)
    }
}