package com.elfeky.domain.usecase.pin.get

import com.elfeky.domain.model.project.Project
import com.elfeky.domain.repo.PinRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPinnedProjectsUseCase @Inject constructor(
    private val repo: PinRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(): Flow<Resource<List<Project>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.getPinnedProjects(accessTokenUseCase.get())
            emit(Resource.Success(data = response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection\n${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred\n${e.message()}"))
        }
    }
}