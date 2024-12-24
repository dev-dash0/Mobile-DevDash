package com.elfeky.devdash.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.elfeky.devdash.ui.done_screen.DoneScreen
import com.elfeky.devdash.ui.main_screen.MainScreen
import com.elfeky.devdash.ui.reset_password_screen.ResetPasswordScreen
import com.elfeky.devdash.ui.sign_in_screen.SignInScreen
import com.elfeky.devdash.ui.sign_up_screen.SignUpScreen
import com.elfeky.devdash.ui.splash_screen.SplashScreen
import com.elfeky.devdash.ui.verify_email_screen.VerifyEmailScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {

        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.SignInScreen.route) {
            SignInScreen(navController = navController)
        }

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }

        composable(
            "${Screen.VerifyEmailScreen.route}/{destination}/{email}",
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

        composable(Screen.ResetPasswordScreen.route) {
            ResetPasswordScreen(navController = navController)
        }

        composable(Screen.DoneScreen.route) {
            DoneScreen(navController = navController)
        }

        composable(Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }

    }
}