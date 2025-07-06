package com.elfeky.domain.usecase.notification

import com.elfeky.domain.model.notification.Notification
import com.elfeky.domain.repo.NotificationRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationRepo: NotificationRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(): Flow<Resource<List<Notification>>> = flow {
        try {
            emit(Resource.Loading())
            val notifications = notificationRepo.getNotifications(accessTokenUseCase.get())
            emit(Resource.Success(notifications))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}