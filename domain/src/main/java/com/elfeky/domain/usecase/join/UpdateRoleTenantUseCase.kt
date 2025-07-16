package com.elfeky.domain.usecase.join

import com.elfeky.domain.model.join.UpdateRoleRequest
import com.elfeky.domain.repo.InviteTenantRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateRoleTenantUseCase @Inject constructor(
    private val repo: InviteTenantRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(tenantId: Int, update: UpdateRoleRequest): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            repo.updateRole(accessTokenUseCase.get(), tenantId, update)
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}