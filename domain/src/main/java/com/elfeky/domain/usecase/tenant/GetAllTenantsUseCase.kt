package com.elfeky.domain.usecase.tenant

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elfeky.domain.model.tenant.Tenant
import com.elfeky.domain.pager.CustomPagerSource
import com.elfeky.domain.repo.TenantRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTenantsUseCase @Inject constructor(
    private val repo: TenantRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(): Flow<PagingData<Tenant>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            initialKey = 1,
            pagingSourceFactory = {
                CustomPagerSource {
                    repo.getTenants(
                        accessToken = accessTokenUseCase.get(),
                        pageNumber = it,
                        pageSize = 20
                    )
                }
            }
        ).flow
    }
}