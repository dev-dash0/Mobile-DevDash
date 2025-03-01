package com.elfeky.devdash.navigation.detail_navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elfeky.devdash.ui.common.Status
import com.elfeky.devdash.ui.screens.details_screens.issue.IssueScreen
import com.elfeky.devdash.ui.screens.details_screens.project.ProjectScreen
import com.elfeky.devdash.ui.screens.details_screens.project.model.ProjectUiModel

@Composable
fun DetailNavigation(id: Int, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = DetailScreen.ProjectScreen.route + "/$id"
    ) {
        composable(
            route = DetailScreen.ProjectScreen.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val companyId = it.arguments?.getInt("id")!!
            ProjectScreen(
                id = companyId, projectList = listOf(
                    ProjectUiModel(
                        0,
                        "Google",
                        "15 Nov | 28 Nov",
                        Status.ToDo,
                        "Gemini",
                        "Enhance Gemini"
                    )
                )
            ) { projectId ->
                navController.navigate(DetailScreen.IssueScreen.route + "/$companyId/$projectId")
            }
        }

        composable(
            route = DetailScreen.IssueScreen.route + "/{company_id}/{project_id}",
            arguments = listOf(
                navArgument("company_id") { type = NavType.IntType },
                navArgument("project_id") { type = NavType.IntType })
        ) {
            val companyId = it.arguments?.getInt("company_id")!!
            val projectId = it.arguments?.getInt("project_id")!!
            IssueScreen(projectId = projectId, companyId = companyId)
        }
    }
}