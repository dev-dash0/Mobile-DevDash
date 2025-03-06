package com.elfeky.domain.model

data class CompanyRequest(
    val description: String,
    val image: String,
    val keywords: String,
    val name: String,
    val tenantUrl: String
)