package com.elfeky.domain.usecase.local_storage

import android.content.SharedPreferences
import com.elfeky.domain.repo.RefreshTokenRepo
import com.elfeky.domain.util.Constants.REFRESH_TOKEN_KEY

class RefreshTokenUseCase(private val sharedPref: SharedPreferences) : RefreshTokenRepo {
    override suspend fun save(refreshToken: String) =
        sharedPref.edit().putString(REFRESH_TOKEN_KEY, refreshToken).apply()

    override suspend fun get(): String? = sharedPref.getString(REFRESH_TOKEN_KEY, "")

    override suspend fun delete() = sharedPref.edit().remove(REFRESH_TOKEN_KEY).apply()
}