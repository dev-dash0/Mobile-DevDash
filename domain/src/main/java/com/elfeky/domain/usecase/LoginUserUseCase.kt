package com.elfeky.domain.usecase

import android.content.Context
import com.elfeky.domain.model.LoginRequest
import com.elfeky.domain.model.LoginResponse
import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.util.Constants.ACCESS_TOKEN_KEY
import com.elfeky.domain.util.Constants.REFRESH_TOKEN_KEY
import com.elfeky.domain.util.Constants.USER_DATA_FILE
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LoginUserUseCase(private val repo: AuthenticationRepo) {

    operator fun invoke(
        loginRequest: LoginRequest,
        context: Context
    ): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.login(loginRequest)
            emit(Resource.Success(data = response))
            storeLoginResponse(loginResponse = response, context = context)
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

    private fun storeLoginResponse(loginResponse: LoginResponse, context: Context) {
        val editor = context.getSharedPreferences(
            USER_DATA_FILE,
            Context.MODE_PRIVATE
        ).edit()
        editor.putString(ACCESS_TOKEN_KEY, loginResponse.accessToken)
        editor.putString(REFRESH_TOKEN_KEY, loginResponse.refreshToken)
        editor.apply()
    }

}