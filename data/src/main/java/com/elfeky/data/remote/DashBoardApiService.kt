package com.elfeky.data.remote

import com.elfeky.domain.model.dashboard.CalendarResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface DashBoardApiService {
    @GET("/api/DashBoard/Calender")
    suspend fun getCalendar(@Header("Authorization") accessToken: String): CalendarResponse
}