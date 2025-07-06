package com.elfeky.devdash.ui.screens.main_screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.navigation.main_navigation.MainNavigation
import com.elfeky.devdash.ui.common.bottom_bar.MainNavigationBar
import com.elfeky.devdash.ui.common.bottom_bar.navigationItems
import com.elfeky.devdash.ui.common.top_bar.TopBar

@Composable
fun MainScreenHost(
    modifier: Modifier = Modifier,
    onSearchNavigate: () -> Unit,
    onNotificationNavigate: () -> Unit,
    onCompanyDetailsNavigate: (id: Int) -> Unit,
    onLogout: () -> Unit
) {
    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var currentTitle by remember { mutableStateOf(navigationItems[0].title) }

    LaunchedEffect(currentDestination) {
        currentDestination?.route?.let { route ->
            val foundItem = navigationItems.find { it.route == route }
            foundItem?.let {
                currentTitle = it.title
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = currentTitle,
                onSearchClick = onSearchNavigate,
                onNotificationClick = onNotificationNavigate
            )
        },
        bottomBar = {
            MainNavigationBar(
                navigationItems = navigationItems,
                isSelected = { route ->
                    currentDestination?.hierarchy?.any { it.route == route } == true
                },
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
            onCompanyDetailsNavigation = onCompanyDetailsNavigate,
            onLogout = onLogout
        )
    }
}