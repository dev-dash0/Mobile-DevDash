package com.elfeky.devdash.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elfeky.devdash.ui.sign_in_screen.SignInScreen
import com.elfeky.devdash.ui.sign_up_screen.SignUpScreen
import com.elfeky.devdash.ui.splash_screen.SplashScreen

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

    }
}