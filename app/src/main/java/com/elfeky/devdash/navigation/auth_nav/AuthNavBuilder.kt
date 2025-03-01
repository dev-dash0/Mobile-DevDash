package com.elfeky.devdash.navigation.auth_nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.elfeky.devdash.navigation.main_nav.HomeRoute
import com.elfeky.devdash.navigation.main_nav.Main
import com.elfeky.devdash.ui.screens.auth_screens.reset_password_screen.ResetPasswordScreen
import com.elfeky.devdash.ui.screens.auth_screens.sign_in_screen.SignInScreen
import com.elfeky.devdash.ui.screens.auth_screens.sign_up_screen.SignUpScreen
import com.elfeky.devdash.ui.screens.auth_screens.verify_email_screen.VerifyEmailScreen

fun NavGraphBuilder.authNavigation(navController: NavController) {
    navigation<Auth>(startDestination = SignInRoute) {
        composable<SignInRoute> {
            SignInScreen(
                onForgetPasswordClick = { navController.navigate(VerifyEmailRoute(it, true)) },
                onSignUpClick = { navController.navigate(SignUpRoute) },
                onSignInClick = {
                    navController.navigate(HomeRoute) {
                        popUpTo<Auth> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<SignUpRoute> {
            SignUpScreen(onSignUpClick = { navController.navigate(VerifyEmailRoute(it, false)) })
        }

        composable<VerifyEmailRoute> {
            val verifyEmailRoute: VerifyEmailRoute = it.toRoute()
            val email = verifyEmailRoute.email

            VerifyEmailScreen(
                email = email,
                onOtpInputFilled = {
                    if (verifyEmailRoute.resetPassword) navController.navigate(
                        ResetPasswordRoute
                    ) else navController.navigate(Main) { popUpTo<Auth> { inclusive = true } }
                }
            )
        }

        composable<ResetPasswordRoute> {
            ResetPasswordScreen(onConfirmClick = { navController.navigate(SignInRoute) })
        }
    }
}