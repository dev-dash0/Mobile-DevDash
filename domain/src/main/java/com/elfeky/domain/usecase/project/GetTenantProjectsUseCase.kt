package com.elfeky.domain.usecase.project

import com.elfeky.domain.model.project.Project
import com.elfeky.domain.repo.ProjectRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTenantProjectsUseCase @Inject constructor(
    private val repo: ProjectRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(id: Int): Flow<Resource<List<Project>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.getTenantProjects(accessTokenUseCase.get(), id)
            emit(Resource.Success(data = response))
        } catch (_: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (_: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}