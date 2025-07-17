package com.elfeky.data.repo

import com.elfeky.data.remote.DashBoardApiService
import com.elfeky.domain.model.dashboard.CalendarDay
import com.elfeky.domain.repo.DashBoardRepo
import javax.inject.Inject

class DashBoardRepoImpl @Inject constructor(
    private val dashBoardApiService: DashBoardApiService
) : DashBoardRepo {
    override suspend fun getCalendar(accessToken: String): List<CalendarDay>? =
        dashBoardApiService.getCalendar(accessToken = "Bearer $accessToken").result

}