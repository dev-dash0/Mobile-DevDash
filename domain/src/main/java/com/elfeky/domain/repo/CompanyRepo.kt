package com.elfeky.domain.repo

import com.elfeky.domain.model.Company
import com.elfeky.domain.model.CompanyRequest

interface CompanyRepo {
    suspend fun createCompany(accessToken: String, request: CompanyRequest)
    suspend fun getCompanies(accessToken: String): List<Company>
    suspend fun getCompanyById(accessToken: String,id: Int): Company
    suspend fun updateCompany(accessToken: String,id: Int, request: CompanyRequest)
    suspend fun deleteCompany(accessToken: String,id: Int)
}