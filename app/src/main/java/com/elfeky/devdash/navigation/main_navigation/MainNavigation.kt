package com.elfeky.devdash.navigation.main_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elfeky.devdash.ui.screens.main_screens.calender.CalenderScreen
import com.elfeky.devdash.ui.screens.main_screens.company.CompanyScreen
import com.elfeky.devdash.ui.screens.main_screens.home.HomeScreen
import com.elfeky.devdash.ui.screens.main_screens.inbox.InboxScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainScreen.HomeScreen.route
    ) {
        composable(route = MainScreen.HomeScreen.route) {
            HomeScreen()
        }
        composable(route = MainScreen.CompanyScreen.route) {
            CompanyScreen()
        }
        composable(route = MainScreen.CalenderScreen.route) {
            CalenderScreen()
        }
        composable(route = MainScreen.InboxScreen.route) {
            InboxScreen()
        }
    }
}