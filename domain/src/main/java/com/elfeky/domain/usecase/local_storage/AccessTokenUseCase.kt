package com.elfeky.domain.usecase.local_storage

import android.content.SharedPreferences
import com.elfeky.domain.util.Constants.ACCESS_TOKEN_KEY

class AccessTokenUseCase(private val sharedPref: SharedPreferences) {
    operator fun invoke(accessToken: String) =
        sharedPref.edit().putString(ACCESS_TOKEN_KEY, accessToken).apply()

    operator fun invoke() = sharedPref.getString(ACCESS_TOKEN_KEY, "")
}