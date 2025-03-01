package com.elfeky.devdash.ui.screens.main_screens.components.bottom_bar

import androidx.annotation.DrawableRes
import com.elfeky.devdash.R
import com.elfeky.devdash.navigation.main_nav.CalenderRoute
import com.elfeky.devdash.navigation.main_nav.CompanyRoute
import com.elfeky.devdash.navigation.main_nav.HomeRoute
import com.elfeky.devdash.navigation.main_nav.InboxRoute

data class BottomNavItem<T : Any>(val title: String, @DrawableRes val icon: Int, val destination: T)

val navigationItems = listOf(
    BottomNavItem(title = "Home", icon = R.drawable.home_ic, HomeRoute),
    BottomNavItem(title = "Company", icon = R.drawable.company_ic, CompanyRoute),
    BottomNavItem(title = "Calender", icon = R.drawable.calender_ic, CalenderRoute),
    BottomNavItem(title = "Inbox", icon = R.drawable.inbox_ic, InboxRoute),
)