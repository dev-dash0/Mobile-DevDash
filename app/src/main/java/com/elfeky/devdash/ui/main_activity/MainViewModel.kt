package com.elfeky.devdash.ui.main_activity

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.domain.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _appState = MutableStateFlow<AppState>(AppState.Loading)
    val appState: StateFlow<AppState> = _appState

    private val _accessToken = MutableStateFlow<String?>(null)
    private val accessToken: StateFlow<String?> = _accessToken

    init {
        loadAccessToken()
        checkAuthenticationStatus()
    }

    private fun loadAccessToken() {
        val token = sharedPreferences.getString(Constants.ACCESS_TOKEN_KEY, null)
        _accessToken.update { token }
    }

    private fun checkAuthenticationStatus() {
        viewModelScope.launch {
            _appState.update {
                if (accessToken.value.isNullOrEmpty()) {
                    AppState.Unauthenticated(AppScreen.SignInScreen.route)
                } else {
                    AppState.Authenticated(AppScreen.MainScreen.route)
                }
            }
        }
    }
}