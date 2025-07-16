package com.elfeky.devdash.main_activity

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.elfeky.devdash.navigation.app_navigation.AppNavigation
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.devdash.ui.screens.main_screens.agent.AgentViewModel
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.gradientBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val agentViewModel: AgentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition {
            viewModel.uiState.value == AuthState.Loading
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )

        setContent {
            var startDestination by remember { mutableStateOf(viewModel.getStartDestination()) }
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(uiState) {
                if (uiState is AuthState.Unauthorized) {
                    startDestination = AppScreen.AuthScreens.route
                }
            }

            DevDashTheme {
                AppNavigation(
                    startDestination = startDestination,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gradientBackground)
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        agentViewModel.startChat(
            text = "create for me a project named zoay with sprints and issues, it is a sustainable e-commerce platform designed to connect eco-conscious consumers with brands that prioritize sustainability, offering products that are ethically sourced, environmentally friendly, and carbon-neutral. The platform includes features like carbon footprint tracking for purchases, rewards for sustainable shopping habits, and a marketplace for upcycled or second-hand goods. Key user features include user registration and profiles, product search and filtering by sustainability score, category, or price, a carbon footprint calculator, wishlists, reward points for eco-friendly purchases, eco-friendly delivery options, and user reviews and ratings. Vendor features encompass onboarding and product listing, sustainability certification verification, sales and inventory management, and an analytics dashboard for sales and customer insights. Admin features include user and vendor management, content management for blogs and sustainability tips, fraud detection, order monitoring, and sustainability score management for products. Innovative features include gamification with badges for sustainable shopping, carbon offset integration, and AI-powered product recommendations based on user preferences and sustainability goals. The tech stack for EcoCart includes a frontend built with React.js or Next.js for server-side rendering and SEO optimization, styled with Tailwind CSS or Material-UI, and managed with Redux or Context API, with a mobile app developed using React Native. The backend utilizes Node.js with Express.js or Django for robust development, with PostgreSQL or MongoDB for the database, OAuth 2.0, JWT, or Firebase Authentication for authentication, Stripe, PayPal, or Razorpay for secure payments, and Elasticsearch or Algolia for fast and scalable product search. This comprehensive approach ensures a seamless, secure, and scalable platform for eco-conscious shopping",
            startDate = "2025-06-17",
            endDate = "2025-08-30",
            tenantId = "7",
        )
    }
}