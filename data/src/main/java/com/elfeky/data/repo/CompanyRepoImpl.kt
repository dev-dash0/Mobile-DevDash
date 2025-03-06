package com.elfeky.data.repo

import com.elfeky.data.remote.CompanyApiService
import com.elfeky.domain.model.Company
import com.elfeky.domain.model.CompanyRequest
import com.elfeky.domain.repo.CompanyRepo

class CompanyRepoImpl(
    private val companyApiService: CompanyApiService
) : CompanyRepo {
    override suspend fun createCompany(accessToken: String, request: CompanyRequest) =
        companyApiService.addCompany(accessToken, request)

    override suspend fun getCompanies(accessToken: String): List<Company> =
        companyApiService.getCompanies(accessToken).result

    override suspend fun getCompanyById(accessToken: String, id: Int): Company =
        companyApiService.getCompanyById(accessToken, id).result.first()


    override suspend fun updateCompany(accessToken: String, id: Int, request: CompanyRequest) =
        companyApiService.updateCompany(accessToken, id, request)


    override suspend fun deleteCompany(accessToken: String, id: Int) =
        companyApiService.deleteCompany(accessToken, id)
}