package com.elfeky.data.repo

import com.elfeky.data.remote.SprintApiService
import com.elfeky.domain.model.sprint.RequestSprint
import com.elfeky.domain.repo.SprintRepo

class SprintRepoImpl(
    private val sprintApiService: SprintApiService
) : SprintRepo {
    override suspend fun createSprint(
        accessToken: String,
        projectId: Int,
        request: RequestSprint
    ) {
        sprintApiService.createSprint(
            accessToken = "Bearer $accessToken",
            projectId = projectId,
            request = request
        )
    }
}