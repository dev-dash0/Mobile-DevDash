package com.elfeky.devdash.ui.screens.auth_screens.sign_in_screen

import com.elfeky.domain.model.account.LoginResponse

data class LoginState(
    val isLoading: Boolean = false,
    val loggedIn: Boolean = false,
    val loginResponse: LoginResponse? = null,
    val error: String = ""
)