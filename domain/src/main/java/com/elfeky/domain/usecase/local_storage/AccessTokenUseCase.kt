package com.elfeky.domain.usecase.local_storage

import android.content.SharedPreferences
import com.elfeky.domain.repo.AccessTokenRepo
import com.elfeky.domain.util.Constants.ACCESS_TOKEN_KEY

class AccessTokenUseCase(private val sharedPref: SharedPreferences) : AccessTokenRepo {
    override suspend fun save(accessToken: String) =
        sharedPref.edit().putString(ACCESS_TOKEN_KEY, accessToken).apply()

    override suspend fun get(): String? = sharedPref.getString(ACCESS_TOKEN_KEY, "")

    override suspend fun delete() = sharedPref.edit().remove(ACCESS_TOKEN_KEY).apply()
}