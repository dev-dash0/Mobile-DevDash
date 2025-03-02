package com.elfeky.devdash.navigation.main_navigation

sealed class MainScreen(val route: String) {
    data object HomeScreen : MainScreen("Home")
    data object CompanyScreen : MainScreen("Company")
    data object ProjectScreen : MainScreen("Project")
    data object CalenderScreen : MainScreen("Calender")
    data object MoreScreen : MainScreen("More")
    data object ProfileScreen : MainScreen("Profile")
}