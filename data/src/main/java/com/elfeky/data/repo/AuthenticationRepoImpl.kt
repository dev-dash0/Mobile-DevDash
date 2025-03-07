package com.elfeky.data.repo

import com.elfeky.data.remote.AuthenticationApiService
import com.elfeky.domain.model.account.ChangePasswordRequest
import com.elfeky.domain.model.account.LoginRequest
import com.elfeky.domain.model.account.LoginResponse
import com.elfeky.domain.model.account.User
import com.elfeky.domain.repo.AuthenticationRepo

class AuthenticationRepoImpl(
    private val authApiService: AuthenticationApiService
) : AuthenticationRepo {
    override suspend fun login(request: LoginRequest): LoginResponse = authApiService.login(request)

    override suspend fun signup(user: User) = authApiService.signup(user)

    override suspend fun getProfile(accessToken: String) = authApiService.getProfile("Bearer $accessToken")

    override suspend fun logout(loginResponse: LoginResponse) {
        authApiService.logout(loginResponse = loginResponse)
    }

    override suspend fun changePassword(
        accessToken: String,
        changePasswordRequest: ChangePasswordRequest
    ) {
        authApiService.changePassword(
            accessToken = "Bearer $accessToken",
            changePasswordRequest = changePasswordRequest
        )
    }

    override suspend fun deleteAccount(accessToken: String) = authApiService.deleteAccount("Bearer $accessToken")

}