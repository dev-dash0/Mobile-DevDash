package com.elfeky.domain.usecase.notification

import com.elfeky.domain.repo.NotificationRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MarkNotificationReadUseCase @Inject constructor(
    private val notificationRepo: NotificationRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(notificationId: Int): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            notificationRepo.markNotificationRead(accessTokenUseCase.get(), notificationId)
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}