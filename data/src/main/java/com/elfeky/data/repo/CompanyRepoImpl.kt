package com.elfeky.data.repo

import com.elfeky.data.remote.CompanyApiService
import com.elfeky.domain.model.CompanyRequest
import com.elfeky.domain.model.Tenant
import com.elfeky.domain.repo.CompanyRepo

class CompanyRepoImpl(
    private val companyApiService: CompanyApiService
) : CompanyRepo {
    override suspend fun createCompany(accessToken: String, request: CompanyRequest) =
        companyApiService.addCompany(accessToken, request)

    override suspend fun getCompanies(accessToken: String): List<Tenant> =
        companyApiService.getCompanies(accessToken).result
}