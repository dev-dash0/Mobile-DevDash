package com.elfeky.domain.repo

interface RefreshTokenRepo {
    suspend fun save(refreshToken: String)
    suspend fun get():String?
    suspend fun delete()
}