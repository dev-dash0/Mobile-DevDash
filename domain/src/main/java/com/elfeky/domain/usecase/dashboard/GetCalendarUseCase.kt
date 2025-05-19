package com.elfeky.domain.usecase.dashboard

import com.elfeky.domain.model.dashboard.CalendarResponse
import com.elfeky.domain.repo.DashBoardRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCalendarUseCase @Inject constructor(
    private val repo: DashBoardRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
) {
    operator fun invoke(): Flow<Resource<CalendarResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.getCalendar(accessTokenUseCase.get())
            emit(Resource.Success(data = response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            if (e.code() == 404) {
                emit(Resource.Error(message = "Calendar is empty"))
            } else {
                emit(Resource.Error(message = "Unexpected error occurred"))
            }

        }
    }
}