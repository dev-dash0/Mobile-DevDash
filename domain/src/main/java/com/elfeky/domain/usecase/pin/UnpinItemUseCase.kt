package com.elfeky.domain.usecase.pin

import com.elfeky.domain.repo.PinRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class UnpinItemUseCase(
    private val repo: PinRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(id: Int, type: String): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            repo.unpinItem(accessTokenUseCase.get(), id, type)
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}