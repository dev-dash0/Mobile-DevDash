package com.elfeky.domain.usecase.local_storage

import android.content.SharedPreferences
import com.elfeky.domain.repo.SharedPreferencesRepo
import com.elfeky.domain.util.Constants.IS_FIRST_LOGIN

class IsFirstLoginUseCase(private val sharedPref: SharedPreferences) :
    SharedPreferencesRepo<Boolean> {
    override fun save(data: Boolean) = sharedPref.edit().putBoolean(IS_FIRST_LOGIN, data).apply()

    override fun get(): Boolean = sharedPref.getBoolean(IS_FIRST_LOGIN, true)

    override fun delete() = sharedPref.edit().remove(IS_FIRST_LOGIN).apply()
}