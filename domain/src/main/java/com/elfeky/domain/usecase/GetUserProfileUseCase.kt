package com.elfeky.domain.usecase

import com.elfeky.domain.model.UserProfile
import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetUserProfileUseCase(private val repo: AuthenticationRepo) {

    operator fun invoke(accessToken: String): Flow<Resource<UserProfile>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.getProfile(accessToken)
            emit(Resource.Success(data = response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}