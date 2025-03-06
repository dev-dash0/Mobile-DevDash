package com.elfeky.domain.usecase.local_storage

import android.content.SharedPreferences
import com.elfeky.domain.util.Constants.REFRESH_TOKEN_KEY

class RefreshTokenUseCase(private val sharedPref: SharedPreferences) {
    operator fun invoke(refreshToken: String) =
        sharedPref.edit().putString(REFRESH_TOKEN_KEY, refreshToken).apply()

    operator fun invoke() = sharedPref.getString(REFRESH_TOKEN_KEY, "")
}