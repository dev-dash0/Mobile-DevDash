package com.elfeky.domain.repo

import com.elfeky.domain.model.CompanyRequest
import com.elfeky.domain.model.Tenant

interface CompanyRepo {
    suspend fun createCompany(accessToken: String, request: CompanyRequest)
    suspend fun getCompanies(accessToken: String): List<Tenant>
}