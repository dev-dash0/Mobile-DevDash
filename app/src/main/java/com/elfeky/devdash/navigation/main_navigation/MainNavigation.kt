package com.elfeky.devdash.navigation.main_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.elfeky.devdash.ui.screens.main_screens.calender.CalenderScreen
import com.elfeky.devdash.ui.screens.main_screens.company.CompanyScreen
import com.elfeky.devdash.ui.screens.main_screens.home.HomeScreen
import com.elfeky.devdash.ui.screens.main_screens.more.MoreScreen
import com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen.ProfileScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    onCompanyDetailsNavigation: (id: Int) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.HomeScreen.route
    ) {
        composable(route = MainScreen.HomeScreen.route) {
            HomeScreen(modifier = modifier)
        }

        composable(route = MainScreen.CompanyScreen.route) {
            CompanyScreen(
                modifier = modifier,
                onCompanyClick = onCompanyDetailsNavigation
            )
        }

        composable(route = MainScreen.CalenderScreen.route) {
            CalenderScreen(modifier = modifier)
        }

        navigation(
            startDestination = MainScreen.SettingsScreen.route,
            route = MainScreen.MoreScreen.route
        ) {
            composable(route = MainScreen.SettingsScreen.route) {
                MoreScreen(onLogout = onLogout) {
                    navController.navigate(MainScreen.ProfileScreen.route) {
                        popUpTo(MainScreen.HomeScreen.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }

            composable(route = MainScreen.ProfileScreen.route) {
                ProfileScreen(modifier = modifier)
            }
        }
    }
}
