package com.elfeky.domain.usecase.local_storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.elfeky.domain.model.account.LoginRequest
import com.elfeky.domain.repo.SharedPreferencesRepo
import com.elfeky.domain.util.Constants.LOGIN_REQUEST
import com.google.gson.Gson
import javax.inject.Inject

class LoginDataUseCase @Inject constructor(private val sharedPref: SharedPreferences) :
    SharedPreferencesRepo<LoginRequest> {
    private val gson = Gson()
    override fun save(data: LoginRequest) =
        sharedPref.edit { putString(LOGIN_REQUEST, gson.toJson(data)) }

    override fun get(): LoginRequest =
        gson.fromJson(sharedPref.getString(LOGIN_REQUEST, ""), LoginRequest::class.java)

    override fun delete() = sharedPref.edit { remove(LOGIN_REQUEST) }
}