package com.elfeky.devdash.navigation.project_details_navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.ui.screens.details_screens.issue.IssueScreen

@Composable
fun ProjectDetailsNavigation(companyId: Int, projectId: Int, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Scaffold(contentWindowInsets = WindowInsets.safeDrawing) { paddingValues ->
        NavHost(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = navController,
            startDestination = ProjectDetailsScreen.IssueScreen.route
        ) {
            composable(route = ProjectDetailsScreen.IssueScreen.route) {
                IssueScreen(projectId = projectId, companyId = companyId)
            }
        }
    }
}