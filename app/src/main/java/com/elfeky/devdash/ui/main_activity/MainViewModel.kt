package com.elfeky.devdash.ui.main_activity

import androidx.lifecycle.ViewModel
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    accessTokenUseCase: AccessTokenUseCase
) : ViewModel() {
    private val accessToken: String? = accessTokenUseCase.get()

    fun getStartDestination(): String =
        if (accessToken.isNullOrEmpty()) AppScreen.SignInScreen.route
        else AppScreen.MainScreen.route
}