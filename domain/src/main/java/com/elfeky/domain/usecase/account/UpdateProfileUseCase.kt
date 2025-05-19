package com.elfeky.domain.usecase.account

import android.util.Log
import com.elfeky.domain.model.account.UserProfileRequest
import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repo: AuthenticationRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
) {
    operator fun invoke(userProfileRequest: UserProfileRequest): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            repo.updateProfile(
                accessTokenUseCase.get(),
                userProfileRequest = userProfileRequest
            )
            Log.i("userProfileRequest",userProfileRequest.toString())
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}