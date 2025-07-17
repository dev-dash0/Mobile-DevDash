package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.dashboard.CalendarDay
import retrofit2.http.GET
import retrofit2.http.Header

interface DashBoardApiService {
    @GET("/api/DashBoard/Calender")
    suspend fun getCalendar(@Header("Authorization") accessToken: String): ServiceResponse<List<CalendarDay>?>
}