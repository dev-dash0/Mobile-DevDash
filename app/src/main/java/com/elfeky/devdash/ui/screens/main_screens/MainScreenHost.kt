package com.elfeky.devdash.ui.screens.main_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.navigation.main_navigation.MainNavigation
import com.elfeky.devdash.ui.screens.main_screens.components.MainNavigationBar
import com.elfeky.devdash.ui.utils.gradientBackground


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    appNavController: NavController
) {

    val mainNavController = rememberNavController()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBackground),
        bottomBar = {
            MainNavigationBar(navController = mainNavController)
        }
    ) { innerPadding ->
        MainNavigation(
            modifier = Modifier.padding(innerPadding),
            appNavController = appNavController,
            mainNavController = mainNavController
        )
    }
}


