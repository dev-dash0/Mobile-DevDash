package com.elfeky.domain.model.dashboard

data class CalendarDay(
    val date: String,
    val issues: List<CalenderIssue>
)
