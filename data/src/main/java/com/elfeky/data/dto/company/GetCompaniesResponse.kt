package com.elfeky.data.dto.company

import com.elfeky.domain.model.Company

data class GetCompaniesResponse(
    val errorMessages: List<String>,
    val isSuccess: Boolean,
    val result: List<Company>,
    val statusCode: Int
)