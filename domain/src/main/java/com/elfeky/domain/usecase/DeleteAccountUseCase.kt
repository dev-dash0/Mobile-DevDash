package com.elfeky.domain.usecase

import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class DeleteAccountUseCase(private val repo: AuthenticationRepo) {
    operator fun invoke(accessToken: String): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            repo.deleteAccount(accessToken)
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}