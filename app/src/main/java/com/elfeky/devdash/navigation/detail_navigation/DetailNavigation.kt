package com.elfeky.devdash.navigation.detail_navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elfeky.devdash.ui.screens.detail_screens.issue.IssueScreen
import com.elfeky.devdash.ui.screens.detail_screens.project.ProjectScreen

@Composable
fun DetailNavigation(id: Int, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = DetailScreen.ProjectScreen.route + "/$id"
    ) {
        composable(
            route = DetailScreen.ProjectScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val projectID = it.arguments?.getInt("id")!!
            ProjectScreen(id = projectID) { id ->
                navController.navigate(DetailScreen.IssueScreen.route + "/$id")
            }
        }

        composable(
            route = DetailScreen.IssueScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val issueID = it.arguments?.getInt("id")!!
            IssueScreen(id = issueID)
        }
    }
}