package com.elfeky.domain.usecase.local_storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.elfeky.domain.repo.SharedPreferencesRepo
import com.elfeky.domain.util.Constants.REFRESH_TOKEN_KEY
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val sharedPref: SharedPreferences) :
    SharedPreferencesRepo<String> {
    override fun save(data: String) =
        sharedPref.edit { putString(REFRESH_TOKEN_KEY, data) }

    override fun get(): String = sharedPref.getString(REFRESH_TOKEN_KEY, "") ?: ""

    override fun delete() = sharedPref.edit { remove(REFRESH_TOKEN_KEY) }
}