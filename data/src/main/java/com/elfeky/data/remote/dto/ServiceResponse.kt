package com.elfeky.data.remote.dto

data class ServiceResponse<T>(
    val errorMessages: List<String>,
    val isSuccess: Boolean,
    val result: T,
    val statusCode: Int
)