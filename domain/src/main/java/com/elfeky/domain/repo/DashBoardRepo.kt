package com.elfeky.domain.repo

import com.elfeky.domain.model.dashboard.CalendarDay

interface DashBoardRepo {
    suspend fun getCalendar(accessToken: String): List<CalendarDay>?
}