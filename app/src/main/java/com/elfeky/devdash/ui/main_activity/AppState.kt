package com.elfeky.devdash.ui.main_activity

sealed class AppState {
    data object Loading : AppState()
    data class Unauthenticated(val startDestination: String) : AppState()
    data class Authenticated(val startDestination: String) : AppState()
}