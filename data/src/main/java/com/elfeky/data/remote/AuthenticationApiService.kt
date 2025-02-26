package com.elfeky.data.remote

import com.elfeky.domain.model.LoginRequest
import com.elfeky.domain.model.LoginResponse
import com.elfeky.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthenticationApiService {
    @POST("/api/Account/Login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/api/Account/Register")
    suspend fun signup(@Body user: User)
}