package com.elfeky.data.dto

data class CRUDResponse<T>(
    val errorMessages: List<String>,
    val isSuccess: Boolean,
    val result: T,
    val statusCode: Int
)