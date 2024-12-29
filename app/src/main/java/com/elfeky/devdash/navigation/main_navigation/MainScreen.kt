package com.elfeky.devdash.navigation.main_navigation

sealed class MainScreen(val route: String) {
    data object HomeScreen : MainScreen("Home")
    data object CompanyScreen : MainScreen("Company")
    data object CalenderScreen : MainScreen("Calender")
    data object InboxScreen : MainScreen("Inbox")
}