package com.elfeky.data.repo

import com.elfeky.data.remote.TenantApiService
import com.elfeky.domain.model.tenant.Tenant
import com.elfeky.domain.model.tenant.TenantRequest
import com.elfeky.domain.repo.TenantRepo
import javax.inject.Inject

class TenantRepoImpl @Inject constructor(
    private val tenantApiService: TenantApiService
) : TenantRepo {
    override suspend fun createTenant(accessToken: String, request: TenantRequest) =
        tenantApiService.addCompany("Bearer $accessToken", request).result.tenant

    override suspend fun getTenants(
        accessToken: String, pageSize: Int,
        pageNumber: Int
    ): List<Tenant> =
        tenantApiService.getCompanies(
            accessToken = "Bearer $accessToken",
            pageSize = pageSize,
            pageNumber = pageNumber
        ).result

    override suspend fun getTenantById(accessToken: String, id: Int): Tenant =
        tenantApiService.getCompanyById("Bearer $accessToken", id).result


    override suspend fun updateTenant(accessToken: String, id: Int, request: TenantRequest) =
        tenantApiService.updateCompany("Bearer $accessToken", id, request).result


    override suspend fun deleteTenant(accessToken: String, id: Int) =
        tenantApiService.deleteCompany("Bearer $accessToken", id).result
}