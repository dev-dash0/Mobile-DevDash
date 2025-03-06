package com.elfeky.domain.repo

interface AccessTokenRepo {
    suspend fun save(accessToken: String)
    suspend fun get():String?
    suspend fun delete()
}