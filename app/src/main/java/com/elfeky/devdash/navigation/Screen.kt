package com.elfeky.devdash.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("SplashScreen")
    object SignInScreen : Screen("SignInScreen")
    object SignUpScreen : Screen("SignUpScreen")
    object VerifyEmailScreen : Screen("VerifyEmailScreen")
    object ResetPasswordScreen: Screen("ResetPasswordScreen")
    object DoneScreen: Screen("DoneScreen")
    object MainScreen: Screen("MainScreen")

}