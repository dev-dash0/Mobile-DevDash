package com.elfeky.devdash.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.get
import com.elfeky.devdash.navigation.auth_nav.Auth
import com.elfeky.devdash.navigation.auth_nav.authNavigation
import com.elfeky.devdash.navigation.main_nav.Main
import com.elfeky.devdash.navigation.main_nav.mainNavigation
import com.elfeky.devdash.ui.screens.main_screens.components.bottom_bar.MainNavigationBar

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = currentDestination?.parent?.equals(navController.graph[Main]) ?: false

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) MainNavigationBar(
                onItemClick = { navController.navigate(it.destination) },
                isSelected = { item ->
                    currentDestination?.hierarchy?.any { it.hasRoute(item.destination::class) } == true
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Auth,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            authNavigation(navController)
            mainNavigation(navController)
        }
    }
}