package com.elfeky.devdash.ui.screens.main_screens.calender

import com.elfeky.domain.model.dashboard.CalenderIssue

data class CalendarScreenState(
    val calender: Map<String, List<CalenderIssue>>? = null,
    val isCalendarLoading: Boolean = false,
    val calendarError: String = ""
)
