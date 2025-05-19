package com.elfeky.domain.usecase.account

import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repo: AuthenticationRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
) {
    operator fun invoke(): Flow<Resource<UserProfile>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.getProfile(accessTokenUseCase.get())
            emit(Resource.Success(data = response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}