package com.elfeky.domain.repo

import com.elfeky.domain.model.LoginRequest
import com.elfeky.domain.model.LoginResponse
import com.elfeky.domain.model.User


interface AuthenticationRepo {
    suspend fun login(request:LoginRequest): LoginResponse
    suspend fun signup(user: User)
}