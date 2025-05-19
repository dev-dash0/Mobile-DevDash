package com.elfeky.domain.usecase.local_storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.elfeky.domain.repo.SharedPreferencesRepo
import com.elfeky.domain.util.Constants.IS_FIRST_LOGIN
import javax.inject.Inject

class IsFirstLoginUseCase @Inject constructor(private val sharedPref: SharedPreferences) :
    SharedPreferencesRepo<Boolean> {
    override fun save(data: Boolean) = sharedPref.edit { putBoolean(IS_FIRST_LOGIN, data) }

    override fun get(): Boolean = sharedPref.getBoolean(IS_FIRST_LOGIN, true)

    override fun delete() = sharedPref.edit { remove(IS_FIRST_LOGIN) }
}