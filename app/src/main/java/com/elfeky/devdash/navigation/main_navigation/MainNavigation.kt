package com.elfeky.devdash.navigation.main_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elfeky.devdash.ui.screens.main_screens.calender.CalenderScreen
import com.elfeky.devdash.ui.screens.main_screens.company.CompanyScreen
import com.elfeky.devdash.ui.screens.main_screens.home.HomeScreen
import com.elfeky.devdash.ui.screens.main_screens.inbox.InboxScreen
import com.elfeky.devdash.ui.screens.main_screens.more.MoreScreen
import com.elfeky.devdash.ui.screens.main_screens.more.components.ProfileScreen

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    appNavController: NavController,
    mainNavController: NavHostController
) {

    NavHost(navController = mainNavController, startDestination = MainScreen.HomeScreen.route) {
        composable(route = MainScreen.HomeScreen.route) {
            HomeScreen(
                mainNavController = mainNavController,
                appNavController = appNavController
            )
        }
        composable(route = MainScreen.CompanyScreen.route) {
            CompanyScreen(
                mainNavController = mainNavController,
                appNavController = appNavController
            )
        }
        composable(route = MainScreen.CalenderScreen.route) {
            CalenderScreen(
                mainNavController = mainNavController,
                appNavController = appNavController
            )
        }
        composable(route = MainScreen.MoreScreen.route) {
            MoreScreen(
                mainNavController = mainNavController,
                appNavController = appNavController
            )
        }
        composable(route = MainScreen.ProfileScreen.route){
            ProfileScreen()
        }
    }
}