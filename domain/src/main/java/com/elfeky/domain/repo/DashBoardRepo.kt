package com.elfeky.domain.repo

import com.elfeky.domain.model.dashboard.CalendarResponse

interface DashBoardRepo {
    suspend fun getCalendar(accessToken: String): CalendarResponse
}