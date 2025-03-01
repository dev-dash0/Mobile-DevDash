package com.elfeky.devdash.navigation.detail_navigation

sealed class DetailScreen(val route: String) {
    data object ProjectScreen : DetailScreen("Project")
    data object IssueScreen : DetailScreen("Issue")
}