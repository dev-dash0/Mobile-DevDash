package com.elfeky.domain.usecase.local_storage

import android.content.SharedPreferences
import android.util.Log
import com.elfeky.domain.model.account.LoginRequest
import com.elfeky.domain.repo.SharedPreferencesRepo
import com.elfeky.domain.util.Constants.LOGIN_REQUEST

class LoginDataUseCase(private val sharedPref: SharedPreferences) :
    SharedPreferencesRepo<LoginRequest> {
    override fun save(data: LoginRequest) =
        sharedPref.edit().putString("loginRequest", "email=${data.email},password=${data.password}")
            .apply()

    override fun get(): LoginRequest = sharedPref.getString(LOGIN_REQUEST, "")?.let {
        Log.i("LoginDataUseCase", it)
        val parts = it.split(",")
        LoginRequest(parts[0].substringAfter("email="), parts[1].substringAfter("password="))
    }!!

    override fun delete() = sharedPref.edit().remove("loginRequest").apply()
}