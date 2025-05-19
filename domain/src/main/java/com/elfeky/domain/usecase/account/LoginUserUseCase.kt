package com.elfeky.domain.usecase.account

import com.elfeky.domain.model.account.LoginRequest
import com.elfeky.domain.model.account.LoginResponse
import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.usecase.local_storage.IsFirstLoginUseCase
import com.elfeky.domain.usecase.local_storage.LoginDataUseCase
import com.elfeky.domain.usecase.local_storage.RefreshTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repo: AuthenticationRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val loginDataUseCase: LoginDataUseCase,
    private val isFirstLoginUseCase: IsFirstLoginUseCase
) {
    operator fun invoke(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.login(loginRequest)
            accessTokenUseCase.save(response.accessToken)
            refreshTokenUseCase.save(response.refreshToken)
            loginDataUseCase.save(loginRequest)
            isFirstLoginUseCase.save(false)
            emit(Resource.Success(data = response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            if (e.code() == 401 || e.code() == 400) {
                emit(Resource.Error(message = "Invalid email or password"))
            } else {
                emit(Resource.Error(message = "Unexpected error occurred"))
            }
        }
    }
}