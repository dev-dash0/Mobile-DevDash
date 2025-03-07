package com.elfeky.domain.repo

interface RefreshTokenRepo {
    fun save(refreshToken: String)
    fun get():String?
    fun delete()
}