package com.elfeky.domain.usecase.project

import com.elfeky.domain.model.project.UpdateProjectRequest
import com.elfeky.domain.repo.ProjectRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateProjectUseCase @Inject constructor(
    private val repo: ProjectRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(id: Int, requestBody: UpdateProjectRequest): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            repo.updateProject(accessTokenUseCase.get(), id, requestBody)
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}