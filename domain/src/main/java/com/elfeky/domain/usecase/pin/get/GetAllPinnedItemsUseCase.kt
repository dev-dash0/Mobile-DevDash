package com.elfeky.domain.usecase.pin.get

import com.elfeky.domain.model.pin.PinnedItems
import com.elfeky.domain.repo.PinRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllPinnedItemsUseCase @Inject constructor(
    private val repo: PinRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(): Flow<Resource<PinnedItems>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.getAllPinnedItems(accessTokenUseCase.get())
            emit(Resource.Success(data = response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection\n${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred\n${e.message()}"))
        }
    }
}