package com.elfeky.devdash.navigation.details_screens_navigation

sealed class ProjectDetailsScreen(val route: String) {
    data object IssueScreen : ProjectDetailsScreen("Issue")
}