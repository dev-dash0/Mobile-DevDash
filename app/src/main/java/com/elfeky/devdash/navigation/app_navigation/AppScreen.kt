package com.elfeky.devdash.navigation.app_navigation

sealed class AppScreen(val route: String) {
    object SplashScreen : AppScreen("SplashScreen")
    object SignInScreen : AppScreen("SignInScreen")
    object SignUpScreen : AppScreen("SignUpScreen")
    object VerifyEmailScreen : AppScreen("VerifyEmailScreen")
    object ResetPasswordScreen: AppScreen("ResetPasswordScreen")
    object DoneScreen: AppScreen("DoneScreen")
    object MainScreen: AppScreen("MainScreen")

}