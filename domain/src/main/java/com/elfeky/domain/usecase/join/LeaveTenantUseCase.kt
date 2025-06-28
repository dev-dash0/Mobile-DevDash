package com.elfeky.domain.usecase.join

import com.elfeky.domain.repo.JoinRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LeaveTenantUseCase @Inject constructor(
    private val repo: JoinRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(tenantId: Int): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            repo.leaveTenant(accessTokenUseCase.get(), tenantId)
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}