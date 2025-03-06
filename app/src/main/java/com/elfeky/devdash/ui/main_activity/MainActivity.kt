package com.elfeky.devdash.ui.main_activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elfeky.devdash.navigation.app_navigation.AppNavigation
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.gradientBackground
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        val mainViewModel: MainViewModel by viewModels()

        setContent {
            val appState by mainViewModel.appState.collectAsStateWithLifecycle()

            var startDestination by remember { mutableStateOf(AppScreen.MainScreen.route) }

            var keepSplashScreen by remember { mutableStateOf(true) }

            splashScreen.setKeepOnScreenCondition { keepSplashScreen }

            DevDashTheme {
                LaunchedEffect(appState) {
                    when (appState) {
                        is AppState.Unauthenticated -> {
                            startDestination =
                                (appState as AppState.Unauthenticated).startDestination
                            delay(100)
                            keepSplashScreen = false
                        }

                        else -> keepSplashScreen = false
                    }
                }

                AppNavigation(
                    startDestination = startDestination,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gradientBackground)
                )
            }
        }
    }
}