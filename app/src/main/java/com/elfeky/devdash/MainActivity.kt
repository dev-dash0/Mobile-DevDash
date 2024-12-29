package com.elfeky.devdash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.elfeky.devdash.navigation.app_navigation.AppNavigation
import com.elfeky.devdash.ui.theme.DevDashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            DevDashTheme {
                AppNavigation()
            }
        }
    }
}

