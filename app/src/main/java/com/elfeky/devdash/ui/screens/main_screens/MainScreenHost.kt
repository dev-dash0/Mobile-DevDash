package com.elfeky.devdash.ui.screens.main_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.navigation.main_navigation.MainNavigation
import com.elfeky.devdash.ui.screens.main_screens.components.MainNavigationBar
import com.elfeky.devdash.ui.utils.gradientBackground


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBackground),
        bottomBar = {
            MainNavigationBar(currentDestination) { route ->
                mainNavController.navigate(
                    route
                )
            }
        }
    ) { innerPadding ->
        MainNavigation(
            modifier = Modifier.padding(innerPadding),
            navController = mainNavController,
        )
    }
}


