package com.elfeky.devdash.ui.main_activity

import androidx.lifecycle.ViewModel
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    accessTokenUseCase: AccessTokenUseCase
) : ViewModel() {
    private var accessToken: String = accessTokenUseCase.get() ?: ""

    fun checkAuthenticationStatus(): Flow<String> = flow {
        if (accessToken.isEmpty()) emit(AppScreen.SignInScreen.route)
        else emit(AppScreen.MainScreen.route)
    }
}