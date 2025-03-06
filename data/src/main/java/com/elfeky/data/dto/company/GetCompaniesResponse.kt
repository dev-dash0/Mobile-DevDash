package com.elfeky.data.dto.company

import com.elfeky.domain.model.Tenant

data class GetCompaniesResponse(
    val errorMessages: List<String>,
    val isSuccess: Boolean,
    val result: List<Tenant>,
    val statusCode: Int
)