package com.elfeky.devdash.ui.screens.main_screens

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


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onProjectNavigate: (id: Int) -> Unit
) {
    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            MainNavigationBar(currentDestination) { route ->
                mainNavController.navigate(
                    route
                )
            }
        }
    ) { innerPadding ->
        MainNavigation(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = mainNavController,
            onProjectNavigate = onProjectNavigate
        )
    }
}


