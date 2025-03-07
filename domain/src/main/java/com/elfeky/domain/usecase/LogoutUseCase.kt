package com.elfeky.domain.usecase

import com.elfeky.domain.model.account.LoginResponse
import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.usecase.local_storage.RefreshTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LogoutUseCase(
    private val repo: AuthenticationRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
) {
    operator fun invoke(): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            repo.logout(
                LoginResponse(
                    accessTokenUseCase.get() ?: "",
                    refreshTokenUseCase.get() ?: ""
                )
            )
            accessTokenUseCase.delete()
            refreshTokenUseCase.delete()
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}