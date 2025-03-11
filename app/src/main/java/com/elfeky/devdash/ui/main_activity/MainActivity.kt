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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.elfeky.devdash.navigation.app_navigation.AppNavigation
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.gradientBackground
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        setContent {
            var startDestination by remember { mutableStateOf<String?>(null) }
            var keepSplashScreenVisible by remember { mutableStateOf(true) }

            LaunchedEffect(key1 = Unit) {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        mainViewModel.checkAuthenticationStatus().collectLatest { destination ->
                            startDestination = destination
                            keepSplashScreenVisible = false
                        }
                    }
                }
            }

            splashScreen.setKeepOnScreenCondition { keepSplashScreenVisible }

            if (startDestination != null) {
                DevDashTheme {
                    AppNavigation(
                        startDestination = startDestination!!,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(gradientBackground)
                    )
                }
            }
        }
    }
}