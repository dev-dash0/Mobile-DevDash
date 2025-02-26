package com.elfeky.devdash.ui.screens.sign_up_screen

data class SignUpState(
    val isLoading: Boolean = false,
    val signedUp: Boolean = false,
    val error: String = ""
)
