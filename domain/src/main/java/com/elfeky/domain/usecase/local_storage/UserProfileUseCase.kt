package com.elfeky.domain.usecase.local_storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.repo.SharedPreferencesRepo
import com.elfeky.domain.util.Constants.USER_PROFILE
import com.google.gson.Gson
import javax.inject.Inject

class UserProfileUseCase @Inject constructor(private val sharedPref: SharedPreferences) :
    SharedPreferencesRepo<UserProfile> {
    private val gson = Gson()
    override fun save(data: UserProfile) =
        sharedPref.edit { putString(USER_PROFILE, gson.toJson(data)) }

    override fun get(): UserProfile =
        gson.fromJson(sharedPref.getString(USER_PROFILE, ""), UserProfile::class.java)

    override fun delete() = sharedPref.edit { remove(USER_PROFILE) }
}