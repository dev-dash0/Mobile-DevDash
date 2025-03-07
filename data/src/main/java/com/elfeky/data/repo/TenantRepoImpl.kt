package com.elfeky.data.repo

import com.elfeky.data.remote.TenantApiService
import com.elfeky.domain.model.tenant.Tenant
import com.elfeky.domain.model.tenant.TenantRequest
import com.elfeky.domain.repo.TenantRepo

class TenantRepoImpl(
    private val tenantApiService: TenantApiService
) : TenantRepo {
    override suspend fun createTenant(accessToken: String, request: TenantRequest) =
        tenantApiService.addCompany(accessToken, request).result.tenant

    override suspend fun getTenants(accessToken: String): List<Tenant> =
        tenantApiService.getCompanies(accessToken).result

    override suspend fun getTenantById(accessToken: String, id: Int): Tenant =
        tenantApiService.getCompanyById(accessToken, id).result


    override suspend fun updateTenant(accessToken: String, id: Int, request: TenantRequest) =
        tenantApiService.updateCompany(accessToken, id, request).result


    override suspend fun deleteTenant(accessToken: String, id: Int) =
        tenantApiService.deleteCompany(accessToken, id).result
}