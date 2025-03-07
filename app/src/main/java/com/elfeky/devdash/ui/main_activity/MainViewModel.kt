package com.elfeky.devdash.ui.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    accessTokenUseCase: AccessTokenUseCase
) : ViewModel() {
    var appState = MutableStateFlow<AppState>(AppState.Loading)
        private set

    private val accessToken = accessTokenUseCase.get()

    init {
        checkAuthenticationStatus()
    }

    private fun checkAuthenticationStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            appState.update {
                if (accessToken.isNullOrEmpty()) {
                    AppState.Unauthenticated(AppScreen.SignInScreen.route)
                } else {
                    AppState.Authenticated(AppScreen.MainScreen.route)
                }
            }
        }
    }
}