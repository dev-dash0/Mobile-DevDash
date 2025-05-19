package com.elfeky.devdash.ui.main_activity

sealed class AuthState {
    object Loading : AuthState()
    object Unauthorized : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}
