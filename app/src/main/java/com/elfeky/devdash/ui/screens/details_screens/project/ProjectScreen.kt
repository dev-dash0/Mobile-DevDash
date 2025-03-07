package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.gradientBackground

@Composable
fun ProjectScreen(
    tenantId: Int,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit,
    viewModel: ProjectViewModel = hiltViewModel()
) {
    val projectState by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getAllProjects(tenantId)
    }

    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.ShowError -> {
                    println("Error: ${event.message}")
                }

                is Event.ProjectCreated -> {
                    println("Project Created")
                }

                is Event.HideCreateDialog -> {
                    println("Hide Create Dialog")
                }
            }
        }
    }

    ProjectScreenContent(
        modifier = modifier,
        onClick = onClick,
        projectState = projectState
    )
}

@Preview
@Composable
private fun ProjectScreenPreview() {
    DevDashTheme {
        ProjectScreen(
            tenantId = 5,
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground),
            onClick = {}
        )
    }
}