package com.elfeky.devdash.ui.common.bottom_bar

import com.elfeky.devdash.R
import com.elfeky.devdash.navigation.main_navigation.MainScreen
import com.elfeky.domain.model.BottomNavigationItem

val navigationItems = listOf(
    BottomNavigationItem(
        title = "Home",
        icon = R.drawable.home_ic,
        route = MainScreen.HomeScreen.route
    ),
    BottomNavigationItem(
        title = "Company",
        icon = R.drawable.company_ic,
        route = MainScreen.CompanyScreen.route
    ),
    BottomNavigationItem(
        title = "Calender",
        icon = R.drawable.calender_ic,
        route = MainScreen.CalenderScreen.route
    ),
    BottomNavigationItem(
        title = "Profile",
        icon = R.drawable.user_ic,
        route = MainScreen.ProfileScreen.route
    ),
)