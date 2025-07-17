package com.elfeky.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ServiceResponse<T>(
    @SerializedName("ErrorMessages", alternate = ["errorMessages"]) val errorMessages: List<String>,
    @SerializedName("IsSuccess", alternate = ["isSuccess"]) val isSuccess: Boolean,
    @SerializedName("Result", alternate = ["result"]) val result: T,
    @SerializedName("StatusCode", alternate = ["statusCode"]) val statusCode: Int
)