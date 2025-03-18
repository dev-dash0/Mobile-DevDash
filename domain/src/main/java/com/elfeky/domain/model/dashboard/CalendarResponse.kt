package com.elfeky.domain.model.dashboard
import com.google.gson.annotations.SerializedName

data class CalendarResponse(
    val statusCode: Int,
    val isSuccess: Boolean,
    val errorMessages: List<String>,
    @SerializedName("result")
    val result: List<CalendarDay>?,

)
