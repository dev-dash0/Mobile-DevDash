package com.elfeky.data.repo

import com.elfeky.data.remote.DashBoardApiService
import com.elfeky.domain.model.dashboard.CalendarResponse
import com.elfeky.domain.repo.DashBoardRepo

class DashBoardRepoImpl(
    private val dashBoardApiService: DashBoardApiService
) : DashBoardRepo {
    override suspend fun getCalendar(accessToken: String): CalendarResponse =
        dashBoardApiService.getCalendar(accessToken = "Bearer $accessToken")

}