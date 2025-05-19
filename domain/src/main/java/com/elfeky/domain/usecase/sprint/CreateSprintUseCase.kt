package com.elfeky.domain.usecase.sprint

import com.elfeky.domain.model.sprint.RequestSprint
import com.elfeky.domain.repo.SprintRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateSprintUseCase @Inject constructor(
    private val repo: SprintRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(projectId: Int, sprint: RequestSprint): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            repo.createSprint(
                accessToken = accessTokenUseCase.get(),
                projectId = projectId,
                request = sprint
            )
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}