package com.elfeky.domain.repo

import com.elfeky.domain.model.account.ChangePasswordRequest
import com.elfeky.domain.model.account.LoginRequest
import com.elfeky.domain.model.account.LoginResponse
import com.elfeky.domain.model.account.User
import com.elfeky.domain.model.account.UserProfile


interface AuthenticationRepo {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun signup(user: User)
    suspend fun getProfile(accessToken: String): UserProfile
    suspend fun logout(loginResponse: LoginResponse)
    suspend fun changePassword(accessToken: String,changePasswordRequest: ChangePasswordRequest)
    suspend fun deleteAccount(accessToken: String)
}