package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.common.ScreenContainer
import com.elfeky.devdash.ui.common.dialogs.project.ProjectDialog
import com.elfeky.devdash.ui.screens.details_screens.project.model.toProjectRequest
import com.elfeky.domain.model.project.UpdateProjectRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit,
    viewModel: ProjectViewModel = hiltViewModel()
) {
    val projectState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.refreshUi() }

    ScreenContainer(
        isLoading = projectState.projects.isEmpty(),
        onCreateClick = { viewModel.openProjectDialog(null) },
        modifier = modifier.fillMaxSize()
    ) {
        ProjectScreenContent(
            modifier = Modifier.fillMaxSize(),
            onClick = onClick,
            projects = projectState.projects,
            pinnedProjects = projectState.pinnedProjects,
            onSwipeToDelete = { viewModel.deleteProject(it) },
            onSwipeToPin = { id ->
                if (viewModel.isPinned(id))
                    viewModel.unpinProject(id)
                else viewModel.pinProject(id)
            },
            onLongPress = { project -> viewModel.openProjectDialog(project) }
        )
    }

    if (projectState.showProjectDialog) {
        ProjectDialog(
            project = projectState.selectedProject?.toProjectRequest(),
            onDismiss = { viewModel.closeProjectDialog() },
            onSubmit = { editedProject ->
                if (projectState.selectedProject != null) {
                    viewModel.updateProject(
                        editedProject = UpdateProjectRequest(
                            id = projectState.selectedProject!!.id,
                            name = editedProject.name,
                            description = editedProject.description,
                            priority = editedProject.priority,
                            status = editedProject.status,
                            isPinned = viewModel.isPinned(projectState.selectedProject!!.id),
                            startDate = editedProject.startDate,
                            endDate = editedProject.endDate
                        ),
                        id = projectState.selectedProject!!.id
                    )
                } else {
                    viewModel.addProject(editedProject)
                }
                viewModel.closeProjectDialog()
            }
        )
    }
}
