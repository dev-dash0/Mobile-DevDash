package com.elfeky.devdash.navigation.main_nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.elfeky.devdash.ui.screens.main_screens.calender.CalenderScreen
import com.elfeky.devdash.ui.screens.main_screens.company.CompanyScreen
import com.elfeky.devdash.ui.screens.main_screens.home.HomeScreen
import com.elfeky.devdash.ui.screens.main_screens.inbox.InboxScreen

fun NavGraphBuilder.mainNavigation(navController: NavController) {
    navigation<Main>(startDestination = HomeRoute) {

        composable<HomeRoute> { HomeScreen() }

        composable<CompanyRoute> { CompanyScreen() }

        composable<CalenderRoute> { CalenderScreen() }

        composable<InboxRoute> { InboxScreen() }
    }
}