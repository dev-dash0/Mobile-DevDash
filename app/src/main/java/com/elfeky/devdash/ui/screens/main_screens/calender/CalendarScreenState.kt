package com.elfeky.devdash.ui.screens.main_screens.calender
import com.elfeky.domain.model.dashboard.CalendarResponse

data class CalendarScreenState(
    val isCalendarLoading: Boolean = true ,
    val calenderList: CalendarResponse? = null,
    val calendarError: String = ""
)
