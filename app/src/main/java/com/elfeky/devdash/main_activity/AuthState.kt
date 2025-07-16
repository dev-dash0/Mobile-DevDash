package com.elfeky.devdash.main_activity

sealed class AuthState {
    object Loading : AuthState()
    object Unauthorized : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}
