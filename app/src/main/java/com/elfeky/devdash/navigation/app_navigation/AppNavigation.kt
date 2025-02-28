package com.elfeky.devdash.navigation.app_navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elfeky.devdash.ui.screens.auth_screens.done_screen.DoneScreen
import com.elfeky.devdash.ui.screens.auth_screens.reset_password_screen.ResetPasswordScreen
import com.elfeky.devdash.ui.screens.auth_screens.sign_in_screen.SignInScreen
import com.elfeky.devdash.ui.screens.auth_screens.sign_up_screen.SignUpScreen
import com.elfeky.devdash.ui.screens.auth_screens.verify_email_screen.VerifyEmailScreen
import com.elfeky.devdash.ui.screens.main_screens.MainScreen
import com.elfeky.devdash.ui.utils.gradientBackground

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.background(gradientBackground),
        navController = navController,
        startDestination = AppScreen.SignInScreen.route
    ) {

        composable(AppScreen.SignInScreen.route) {
            SignInScreen(navController = navController)
        }

        composable(AppScreen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }

        composable(
            "${AppScreen.VerifyEmailScreen.route}/{destination}/{email}",
            arguments = listOf(
                navArgument("destination") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            ),
        ) {
            val destination = it.arguments?.getString("destination")!!
            val email = it.arguments?.getString("email")!!

            VerifyEmailScreen(
                navController = navController,
                destination = destination,
                email = email
            )
        }

        composable(AppScreen.ResetPasswordScreen.route) {
            ResetPasswordScreen(navController = navController)
        }

        composable(AppScreen.DoneScreen.route) {
            DoneScreen(navController = navController)
        }

        composable(AppScreen.MainScreen.route) {
            MainScreen(appNavController = navController)
        }

    }
}