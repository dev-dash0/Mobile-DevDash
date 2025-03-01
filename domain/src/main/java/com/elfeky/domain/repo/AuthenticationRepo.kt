package com.elfeky.domain.repo

import com.elfeky.domain.model.LoginRequest
import com.elfeky.domain.model.LoginResponse
import com.elfeky.domain.model.User
import com.elfeky.domain.model.UserProfile


interface AuthenticationRepo {
    suspend fun login(request:LoginRequest): LoginResponse
    suspend fun signup(user: User)
    suspend fun getProfile(accessToken: String): UserProfile
    suspend fun logout(loginResponse: LoginResponse)
}