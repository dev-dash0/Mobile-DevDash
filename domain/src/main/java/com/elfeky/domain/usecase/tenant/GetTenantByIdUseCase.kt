package com.elfeky.domain.usecase.tenant

import com.elfeky.domain.model.tenant.Tenant
import com.elfeky.domain.repo.TenantRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTenantByIdUseCase @Inject constructor(
    private val repo: TenantRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(id: Int): Flow<Resource<Tenant>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.getTenantById(accessTokenUseCase.get(), id)
            emit(Resource.Success(response))
        } catch (_: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (_: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}