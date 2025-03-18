package com.elfeky.devdash.ui.main_activity

import androidx.lifecycle.ViewModel
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.domain.usecase.local_storage.IsFirstLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    isFirstLoginUseCase: IsFirstLoginUseCase
) : ViewModel() {
    private val isFirstLogin = isFirstLoginUseCase.get()

    fun getStartDestination(): String =
        if (isFirstLogin) AppScreen.SignInScreen.route
        else AppScreen.MainScreen.route
}