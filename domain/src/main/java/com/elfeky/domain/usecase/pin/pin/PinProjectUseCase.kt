package com.elfeky.domain.usecase.pin.pin

import com.elfeky.domain.repo.PinRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PinProjectUseCase @Inject constructor(
    private val repo: PinRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(id: Int): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            repo.pinProject(accessTokenUseCase.get(), id)
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection\n${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred\n${e.message()}"))
        }
    }
}