package com.elfeky.devdash.navigation.app_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elfeky.devdash.ui.screens.details_screens.company.CompanyDetailsScreen
import com.elfeky.devdash.ui.screens.details_screens.company.CompanyDetailsViewModel
import com.elfeky.devdash.ui.screens.details_screens.company.components.chat_bot.ChatViewModel
import com.elfeky.devdash.ui.screens.details_screens.project.ProjectDetailsScreen
import com.elfeky.devdash.ui.screens.details_screens.project.ProjectDetailsViewModel
import com.elfeky.devdash.ui.screens.details_screens.sprint.SprintScreen
import com.elfeky.devdash.ui.screens.details_screens.sprint.SprintViewModel
import com.elfeky.devdash.ui.screens.extra_screens.notifications.NotificationScreen
import com.elfeky.devdash.ui.screens.extra_screens.search.SearchScreen
import com.elfeky.devdash.ui.screens.main_screens.MainScreenHost

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
            MainScreenHost(
                onSearchNavigate = { navController.navigate(AppScreen.SearchScreen.route) },
                onNotificationNavigate = { navController.navigate(AppScreen.NotificationScreen.route) },
                onCompanyDetailsNavigate = { navController.navigate(AppScreen.CompanyDetails.route + "/$it") },
                onProjectDetailsNavigate = { navController.navigate(AppScreen.ProjectDetailsScreen.route + "/$it") },
                onSprintNavigate = { id, role -> navController.navigate(AppScreen.SprintScreen.route + "/$id/$role") },
                onLogout = {
                    navController.navigate(AppScreen.AuthScreens.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = AppScreen.CompanyDetails.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val companyId = it.arguments?.getInt("id")!!

            CompanyDetailsScreen(
                modifier = modifier,
                onBackClick = { navController.popBackStack() },
                onNavigateToProject = { projectId ->
                    navController.navigate(AppScreen.ProjectDetailsScreen.route + "/$projectId")
                },
                companyViewModel = hiltViewModel<CompanyDetailsViewModel, CompanyDetailsViewModel.Factory>(
                    key = companyId.toString()
                ) {
                    it.create(companyId)
                },
                chatViewModel = hiltViewModel<ChatViewModel, ChatViewModel.Factory>(
                    key = "chat view model"
                ) {
                    it.create(companyId)
                },
            )
        }

        composable(
            AppScreen.ProjectDetailsScreen.route + "/{project_id}",
            arguments = listOf(navArgument("project_id") { type = NavType.IntType })
        ) {
            val projectId = it.arguments?.getInt("project_id")!!

            ProjectDetailsScreen(
                viewModel = hiltViewModel<ProjectDetailsViewModel, ProjectDetailsViewModel.Factory>(
                    key = projectId.toString()
                ) {
                    it.create(projectId)
                },
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSprintDetails = { sprintId, role ->
                    navController.navigate(AppScreen.SprintScreen.route + "/$sprintId" + "/$role")
                }
            )
        }

        composable(
            AppScreen.SprintScreen.route + "/{sprint_id}" + "/{role}",
            arguments = listOf(
                navArgument("sprint_id") { type = NavType.IntType },
                navArgument("role") { type = NavType.StringType }
            )
        ) {
            val sprintId = it.arguments?.getInt("sprint_id")!!
            val role = it.arguments?.getString("role")!!

            SprintScreen(
                role = role,
                viewModel = hiltViewModel<SprintViewModel, SprintViewModel.Factory>(
                    key = sprintId.toString()
                ) { it.create(sprintId) },
                onBackClick = { navController.popBackStack() },
                onIssueClick = {}
            )
        }

        composable(AppScreen.NotificationScreen.route) {
            NotificationScreen { navController.popBackStack() }
        }

        composable(AppScreen.SearchScreen.route) {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCompanyDetails = { navController.navigate(AppScreen.CompanyDetails.route + "/$it") },
                onNavigateToProjectDetails = { navController.navigate(AppScreen.ProjectDetailsScreen.route + "/$it") },
                onNavigateToIssueDetails = { },
            )
        }
    }
}