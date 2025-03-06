package com.elfeky.domain.usecase.company

import com.elfeky.domain.model.CompanyRequest
import com.elfeky.domain.repo.CompanyRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AddCompanyUseCase(
    private val repo: CompanyRepo, private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(request: CompanyRequest): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            repo.createCompany(accessTokenUseCase.get() ?: "", request)
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}