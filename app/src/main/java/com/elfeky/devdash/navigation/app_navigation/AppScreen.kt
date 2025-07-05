package com.elfeky.devdash.navigation.app_navigation

sealed class AppScreen(val route: String) {
    data object AuthScreens : AppScreen("AuthScreens")
    data object SignInScreen : AppScreen("SignInScreen")
    data object SignUpScreen : AppScreen("SignUpScreen")
    data object VerifyEmailScreen : AppScreen("VerifyEmailScreen")
    data object ResetPasswordScreen : AppScreen("ResetPasswordScreen")
    data object DoneScreen : AppScreen("DoneScreen")
    data object MainScreen : AppScreen("MainScreen")
    data object ProjectDetailsScreen : AppScreen("ProjectDetailsScreen")
    data object SprintScreen : AppScreen("SprintScreen")
    data object NotificationScreen : AppScreen("NotificationScreen")
    data object SearchScreen : AppScreen("SearchScreen")
}