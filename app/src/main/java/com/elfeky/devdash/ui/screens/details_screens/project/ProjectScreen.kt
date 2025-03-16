package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.common.ScreenContainer
import com.elfeky.devdash.ui.common.component.LoadingIndicator
import com.elfeky.devdash.ui.common.dialogs.project.ProjectDialog
import com.elfeky.devdash.ui.screens.details_screens.project.model.ProjectState
import com.elfeky.devdash.ui.screens.details_screens.project.model.toProjectRequest
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.gradientBackground
import com.elfeky.domain.model.project.UpdateProjectRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(
    tenantId: Int,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit,
    viewModel: ProjectViewModel = hiltViewModel()
) {
    val projectState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.getAllProjects(tenantId) }

    LaunchedEffect(projectState.updateUi) {
        if (projectState.updateUi)
            viewModel.getAllProjects(tenantId)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.openProjectDialog(null) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        AnimatedVisibility(
            visible = projectState.isLoading,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            LoadingIndicator()
        }

        AnimatedVisibility(
            visible = !projectState.isLoading,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(animationSpec = tween(500))
        ) {
            ProjectScreenContent(
                modifier = Modifier.fillMaxSize(),
                onClick = onClick,
                projectState = projectState,
                onSwipeToDelete = {
                    viewModel.deleteProject(it)
                    projectState.projectDeleted
                },
                onSwipeToPin = {/*TODO*/ },
                onLongPress = { project -> viewModel.openProjectDialog(project) }
            )
        }
    }

    if (projectState.showProjectDialog) {
        ProjectDialog(
            project = projectState.selectedProject?.toProjectRequest(),
            dateRangeState = rememberDateRangePickerState(),
            onDismiss = { viewModel.closeProjectDialog() },
            onSubmit = { editedProject ->
                if (projectState.selectedProject != null) {
                    viewModel.updateProject(
                        UpdateProjectRequest(
                            id = projectState.selectedProject!!.id,
                            name = editedProject.name,
                            description = editedProject.description,
                            priority = editedProject.priority,
                            status = editedProject.status,
                            isPinned = false,
                            startDate = editedProject.startDate,
                            endDate = editedProject.endDate
                        ), projectState.selectedProject!!.id
                    )
                } else {
                    viewModel.addProject(editedProject, tenantId)
                }
                viewModel.closeProjectDialog()
            }
        )
    }
}

@Preview
@Composable
private fun ProjectScreenPreview() {
    DevDashTheme {
        ScreenContainer(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground),
            onCreateClick = {},
        ) {
            ProjectScreenContent(
                modifier = Modifier.fillMaxSize(),
                onClick = {},
                projectState = ProjectState(),
                onSwipeToDelete = { true },
                onSwipeToPin = {},
                onLongPress = {}
            )
        }
    }
}