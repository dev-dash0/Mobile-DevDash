package com.elfeky.devdash.ui.screens.main_screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.navigation.main_navigation.MainNavigation
import com.elfeky.devdash.ui.common.bottom_bar.MainNavigationBar

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onCompanyDetailsNavigation: (id: Int) -> Unit,
    onLogout: () -> Unit
) {
    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    Scaffold(
        modifier = modifier,
        bottomBar = {
            MainNavigationBar(
                isSelected = { route -> currentDestination?.hierarchy?.any { it.route == route } == true },
                onItemClick = { route ->
                    mainNavController.navigate(route) { launchSingleTop = true }
                }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        MainNavigation(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = mainNavController,
            onCompanyDetailsNavigation = onCompanyDetailsNavigation,
            onLogout = onLogout
        )
    }
}


