package com.elfeky.domain.repo

interface SharedPreferencesRepo<T> {
    fun save(data: T)
    fun get():T
    fun delete()
}