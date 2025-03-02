package com.elfeky.devdash.ui.screens.main_screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.navigation.main_navigation.MainNavigation
import com.elfeky.devdash.ui.common.bottom_bar.MainNavigationBar
import com.elfeky.devdash.ui.utils.gradientBackground
import com.elfeky.domain.util.Constants.ACCESS_TOKEN_KEY
import com.elfeky.domain.util.Constants.REFRESH_TOKEN_KEY
import com.elfeky.domain.util.Constants.USER_DATA_FILE


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
    onProjectDetailsNavigate: (companyId: Int, projectId: Int) -> Unit,
) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE)
    val savedAccessToken = sharedPref.getString(ACCESS_TOKEN_KEY, "")
    val savedRefreshToken = sharedPref.getString(REFRESH_TOKEN_KEY, "")

    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBackground),
        bottomBar = {
            MainNavigationBar(
                isSelected = { route -> currentDestination?.hierarchy?.any { it.route == route } == true },
                onItemClick = { route -> mainNavController.navigate(route) })
        },
        contentWindowInsets = WindowInsets.safeContent
    ) { innerPadding ->
        MainNavigation(
            modifier = Modifier.padding(innerPadding),
            navController = mainNavController,
            accessToken = savedAccessToken.toString(),
            refreshToken = savedRefreshToken.toString(),
            onLogout = onLogout,
            onProjectDetailsNavigate = onProjectDetailsNavigate
        )
    }
}


