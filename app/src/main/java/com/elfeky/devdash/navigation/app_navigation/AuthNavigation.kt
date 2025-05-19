package com.elfeky.devdash.navigation.app_navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.elfeky.devdash.ui.screens.auth_screens.done_screen.DoneScreen
import com.elfeky.devdash.ui.screens.auth_screens.reset_password_screen.ResetPasswordScreen
import com.elfeky.devdash.ui.screens.auth_screens.sign_in_screen.SignInScreen
import com.elfeky.devdash.ui.screens.auth_screens.sign_up_screen.SignUpScreen
import com.elfeky.devdash.ui.screens.auth_screens.verify_email_screen.VerifyEmailScreen

fun NavGraphBuilder.authNavigation(navController: NavHostController) {
    navigation(
        startDestination = AppScreen.SignInScreen.route,
        route = AppScreen.AuthScreens.route
    ) {
        composable(AppScreen.SignInScreen.route) {
            SignInScreen(
                onSignInSuccess = {
                    navController.navigate(AppScreen.MainScreen.route) {
                        popUpTo(AppScreen.AuthScreens.route) {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = { navController.navigate(AppScreen.SignUpScreen.route) },
                onForgotPasswordClick = { email ->
                    navController.navigate(
                        "${AppScreen.VerifyEmailScreen.route}/${AppScreen.ResetPasswordScreen.route}/$email"
                    )
                }
            )
        }

        composable(AppScreen.SignUpScreen.route) {
            SignUpScreen(
                onSignedUp = { email ->
                    navController.navigate(AppScreen.VerifyEmailScreen.route + "/${AppScreen.SignInScreen.route}/$email")
                }
            )
        }

        composable(
            AppScreen.VerifyEmailScreen.route + "/{destination}/{email}",
            arguments = listOf(
                navArgument("destination") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            ),
        ) {
            val destination = it.arguments?.getString("destination")!!
            val email = it.arguments?.getString("email")!!

            VerifyEmailScreen(
                onOtpInputFilled = {
                    navController.navigate(destination) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
                email = email
            )
        }

        composable(AppScreen.ResetPasswordScreen.route) {
            ResetPasswordScreen { navController.navigate(AppScreen.DoneScreen.route) }
        }

        composable(AppScreen.DoneScreen.route) {
            DoneScreen {
                navController.navigate(AppScreen.SignInScreen.route) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
        }
    }
}