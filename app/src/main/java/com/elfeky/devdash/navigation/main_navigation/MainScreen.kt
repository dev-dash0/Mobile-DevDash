package com.elfeky.devdash.navigation.main_navigation

sealed class MainScreen(val route: String) {
    object HomeScreen : MainScreen("Home")
    object CompanyScreen : MainScreen("Company")
    object CalenderScreen : MainScreen("Calender")
    object InboxScreen : MainScreen("Inbox")
}