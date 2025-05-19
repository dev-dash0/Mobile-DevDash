package com.elfeky.devdash.navigation.app_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elfeky.devdash.navigation.details_screens_navigation.ProjectDetailsNavigation
import com.elfeky.devdash.navigation.main_navigation.MainScreen
import com.elfeky.devdash.ui.screens.details_screens.company.CompanyDetailsScreen
import com.elfeky.devdash.ui.screens.details_screens.company.CompanyDetailsViewModel
import com.elfeky.devdash.ui.screens.main_screens.MainScreen

@Composable
fun AppNavigation(
    startDestination: String,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        authNavigation(navController)

        composable(AppScreen.MainScreen.route) {
            MainScreen(
                onCompanyDetailsNavigation = { navController.navigate(MainScreen.CompanyDetails.route + "/$it") },
                onLogout = {
                    navController.navigate(AppScreen.SignInScreen.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = MainScreen.CompanyDetails.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val companyId = it.arguments?.getInt("id")!!

            CompanyDetailsScreen(
                modifier = modifier,
                onBackClick = { navController.popBackStack() },
                onNavigateToProject = { projectId ->
                    navController.navigate(AppScreen.DetailScreen.route + "/$companyId/$projectId")
                },
                viewModel = hiltViewModel<CompanyDetailsViewModel, CompanyDetailsViewModel.Factory>(
                    key = companyId.toString()
                ) {
                    it.create(
                        companyId
                    )
                }
            )
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