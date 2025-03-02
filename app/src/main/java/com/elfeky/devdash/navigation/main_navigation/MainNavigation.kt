package com.elfeky.devdash.navigation.main_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.elfeky.devdash.ui.screens.details_screens.project.ProjectScreen
import com.elfeky.devdash.ui.screens.main_screens.calender.CalenderScreen
import com.elfeky.devdash.ui.screens.main_screens.company.CompanyScreen
import com.elfeky.devdash.ui.screens.main_screens.home.HomeScreen
import com.elfeky.devdash.ui.screens.main_screens.more.MoreScreen
import com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen.ProfileScreen

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    accessToken: String,
    refreshToken: String,
    onLogout: () -> Unit,
    onProjectDetailsNavigate: (companyId: Int, projectId: Int) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.HomeScreen.route
    ) {
        composable(route = MainScreen.HomeScreen.route) {
            HomeScreen(
                modifier = modifier,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
        composable(route = MainScreen.CompanyScreen.route) {
            CompanyScreen(
                modifier = modifier,
                accessToken = accessToken,
                refreshToken = refreshToken
            ) {
                navController.navigate(MainScreen.ProjectScreen.route + "/$it")
            }
        }
        composable(
            route = MainScreen.ProjectScreen.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val companyId = it.arguments?.getInt("id")!!
            ProjectScreen(
                id = companyId,
                onClick = { projectId -> onProjectDetailsNavigate(companyId, projectId) })
        }
        composable(route = MainScreen.CalenderScreen.route) {
            CalenderScreen(
                modifier = modifier,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
        composable(route = MainScreen.MoreScreen.route) {
            MoreScreen(
                accessToken = accessToken,
                refreshToken = refreshToken,
                onLogout = onLogout,
                onProfileNavigate = { navController.navigate(MainScreen.ProfileScreen.route) }
            )
        }
        composable(route = MainScreen.ProfileScreen.route) {
            ProfileScreen(
                modifier = modifier,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }
}