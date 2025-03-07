package com.elfeky.domain.usecase

import com.elfeky.domain.model.account.User
import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RegisterUserUseCase(private val repo: AuthenticationRepo) {
    operator fun invoke(user: User): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            repo.signup(user)
            emit(Resource.Success())
        }catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            if (e.code() == 400) {
                emit(Resource.Error(message = "Email is already registered"))
            } else {
                emit(Resource.Error(message = "Unexpected error occurred"))
            }
        }
    }
}