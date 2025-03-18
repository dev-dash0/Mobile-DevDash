package com.elfeky.devdash.ui.screens.main_screens.home

import com.elfeky.domain.model.account.LoginResponse

data class HomeState(
    val isLoading: Boolean = false,
    val error: String = "",
    val loggedIn: Boolean = false,
    val loginResponse: LoginResponse? = null,
)
