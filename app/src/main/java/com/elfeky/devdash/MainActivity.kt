package com.elfeky.devdash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.elfeky.devdash.navigation.app_navigation.AppNavigation
import com.elfeky.devdash.ui.theme.DevDashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DevDashTheme {
                AppNavigation()
            }
        }
    }
}

