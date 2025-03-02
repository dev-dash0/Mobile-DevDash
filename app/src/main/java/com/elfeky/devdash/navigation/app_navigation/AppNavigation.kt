package com.elfeky.devdash.navigation.app_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elfeky.devdash.navigation.project_details_navigation.ProjectDetailsNavigation
import com.elfeky.devdash.ui.screens.auth_screens.done_screen.DoneScreen
import com.elfeky.devdash.ui.screens.auth_screens.reset_password_screen.ResetPasswordScreen
import com.elfeky.devdash.ui.screens.auth_screens.sign_in_screen.SignInScreen
import com.elfeky.devdash.ui.screens.auth_screens.sign_up_screen.SignUpScreen
import com.elfeky.devdash.ui.screens.auth_screens.verify_email_screen.VerifyEmailScreen
import com.elfeky.devdash.ui.screens.main_screens.MainScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppScreen.SignInScreen.route
    ) {
        composable(AppScreen.SignInScreen.route) {
            SignInScreen(
                onSignInClick = {
                    navController.navigate(AppScreen.MainScreen.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = { navController.navigate(AppScreen.SignUpScreen.route) },
                onForgetPassword = { email ->
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
            "${AppScreen.VerifyEmailScreen.route}/{destination}/{email}",
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

        composable(AppScreen.MainScreen.route) {
            MainScreen(onLogout = {
                navController.navigate(AppScreen.SignInScreen.route) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }) { companyId, projectId ->
                navController.navigate(AppScreen.DetailScreen.route + "/$companyId/$projectId")
            }
        }

        composable(
            AppScreen.DetailScreen.route + "/{company_id}/{project_id}",
            arguments = listOf(
                navArgument("company_id") { type = NavType.IntType },
                navArgument("project_id") { type = NavType.IntType }
            )
        ) {
            val companyId = it.arguments?.getInt("company_id")!!
            val projectId = it.arguments?.getInt("project_id")!!
            ProjectDetailsNavigation(companyId, projectId)
        }
    }
}