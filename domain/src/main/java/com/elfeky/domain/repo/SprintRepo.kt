package com.elfeky.domain.repo

import com.elfeky.domain.model.sprint.RequestSprint

interface SprintRepo {
    suspend fun createSprint(accessToken: String, projectId: Int, request: RequestSprint)
}