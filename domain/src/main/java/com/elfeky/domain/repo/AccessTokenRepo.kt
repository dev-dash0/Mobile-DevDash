package com.elfeky.domain.repo

interface AccessTokenRepo {
    fun save(accessToken: String)
    fun get():String?
    fun delete()
}