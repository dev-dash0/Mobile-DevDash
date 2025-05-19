package com.elfeky.domain.usecase.tenant

import com.elfeky.domain.model.tenant.TenantRequest
import com.elfeky.domain.repo.TenantRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateTenantUseCase @Inject constructor(
    private val repo: TenantRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(id: Int, requestBody: TenantRequest): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            repo.updateTenant(accessTokenUseCase.get(), id, requestBody)
            emit(Resource.Success())
        } catch (_: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (_: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}