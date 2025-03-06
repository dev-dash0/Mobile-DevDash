package com.elfeky.data.remote

import com.elfeky.domain.model.ChangePasswordRequest
import com.elfeky.domain.model.LoginRequest
import com.elfeky.domain.model.LoginResponse
import com.elfeky.domain.model.User
import com.elfeky.domain.model.UserProfile
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface AuthenticationApiService {
    @POST("/api/Account/Login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/api/Account/Register")
    suspend fun signup(@Body user: User)

    @GET("/api/Account/Profile")
    suspend fun getProfile(@Header("Authorization") accessToken: String): UserProfile

    @POST("/api/Account/Logout")
    suspend fun logout(@Body loginResponse: LoginResponse)

    @POST("/api/Account/ChangePassword")
    suspend fun changePassword(
        @Header("Authorization") accessToken: String,
        @Body changePasswordRequest: ChangePasswordRequest
    )


    @DELETE("/api/Account/RemoveAccount")
    suspend fun deleteAccount(@Header("Authorization") accessToken: String)

}