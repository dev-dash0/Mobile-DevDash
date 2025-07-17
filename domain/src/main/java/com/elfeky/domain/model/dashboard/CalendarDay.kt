package com.elfeky.domain.model.dashboard

import com.google.gson.annotations.SerializedName

data class CalendarDay(
    @SerializedName("Date", alternate = ["date"]) val date: String,
    @SerializedName("Issues", alternate = ["issues"]) val issues: List<CalenderIssue>
)
