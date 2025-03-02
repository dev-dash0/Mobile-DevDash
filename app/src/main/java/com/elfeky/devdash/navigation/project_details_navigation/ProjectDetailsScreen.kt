package com.elfeky.devdash.navigation.project_details_navigation

sealed class ProjectDetailsScreen(val route: String) {
    data object IssueScreen : ProjectDetailsScreen("Issue")
}