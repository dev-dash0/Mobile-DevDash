package com.elfeky.devdash.ui.screens.main_screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.navigation.main_navigation.MainNavigation
import com.elfeky.devdash.ui.common.component.TopBar
import com.elfeky.devdash.ui.screens.main_screens.components.MainNavigationBar

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    appNavController: NavController
) {

    val mainNavController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            MainNavigationBar(navController = mainNavController)
        },
        topBar = {
            TopBar("Home", { mainNavController.navigateUp() })
        }
    ) { innerPadding ->
        MainNavigation(
            modifier = Modifier.padding(innerPadding),
            appNavController = appNavController,
            mainNavController = mainNavController
        )
    }
}


