package com.elfeky.devdash.navigation.main_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elfeky.devdash.ui.screens.main_screens.calender.CalenderScreen
import com.elfeky.devdash.ui.screens.main_screens.company.CompanyScreen
import com.elfeky.devdash.ui.screens.main_screens.home.HomeScreen
import com.elfeky.devdash.ui.screens.main_screens.profile.ProfileScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    onCompanyDetailsNavigate: (id: Int) -> Unit,
    onProjectDetailsNavigate: (id: Int) -> Unit,
    onSprintNavigate: (id: Int, role: String) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.HomeScreen.route,
        modifier = modifier
    ) {
        composable(route = MainScreen.HomeScreen.route) {
            HomeScreen(
                navigateToCompany = onCompanyDetailsNavigate,
                navigateToProject = onProjectDetailsNavigate,
                navigateToSprint = onSprintNavigate
            )
        }

        composable(route = MainScreen.CompanyScreen.route) {
            CompanyScreen(onNavigateToCompanyDetails = onCompanyDetailsNavigate)
        }

        composable(route = MainScreen.CalenderScreen.route) {
            CalenderScreen()
        }

//        navigation(
//            startDestination = MainScreen.SettingsScreen.route,
//            route = MainScreen.MoreScreen.route
//        ) {
//            composable(route = MainScreen.SettingsScreen.route) {
//                MoreScreen(onLogout = onLogout) {
//                    navController.navigate(MainScreen.ProfileScreen.route) {
//                        popUpTo(MainScreen.HomeScreen.route) { inclusive = false }
//                        launchSingleTop = true
//                    }
//                }
//            }

        composable(route = MainScreen.ProfileScreen.route) {
            ProfileScreen(onLogout = onLogout)
//            }
        }
    }
}
