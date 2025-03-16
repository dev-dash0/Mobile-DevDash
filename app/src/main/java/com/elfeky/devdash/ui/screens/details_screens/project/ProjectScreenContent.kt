package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.card.ProjectCard
import com.elfeky.devdash.ui.common.component.LoadingIndicator
import com.elfeky.devdash.ui.common.component.SwipeToDismissItem
import com.elfeky.devdash.ui.common.toStatus
import com.elfeky.devdash.ui.screens.details_screens.project.model.ProjectState
import com.elfeky.devdash.ui.screens.details_screens.project.model.projectList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.project.Project
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun ProjectScreenContent(
    projectState: ProjectState,
    onClick: (id: Int) -> Unit,
    onSwipeToDelete: (id: Int) -> Boolean,
    onSwipeToPin: (id: Int) -> Unit,
    onLongPress: (project: Project) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (projectState.isLoading) {
            item {
                LoadingIndicator()
            }
        } else {
            items(items = projectState.projects, key = { it.id }) { project ->

                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { dismissValue ->
                        when (dismissValue) {
                            SwipeToDismissBoxValue.StartToEnd -> {
                                onSwipeToPin(project.id)
                                false
                            }

                            SwipeToDismissBoxValue.EndToStart -> {
                                scope.launch { delay(1.seconds) }
                                onSwipeToDelete(project.id)

                            }

                            SwipeToDismissBoxValue.Settled -> false
                        }
                    },
                    positionalThreshold = { totalDistance -> totalDistance * .5f }
                )

                SwipeToDismissItem(dismissState) {
                    ProjectCard(
                        date = project.endDate,
                        status = project.status.toStatus(),
                        issueTitle = project.name,
                        description = project.description,
                        onClick = { onClick(project.id) },
                        onLongClick = { onLongPress(project) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProjectScreenContentPreview() {
    val projectState =
        remember { mutableStateOf(ProjectState(isLoading = false, projects = projectList)) }
    DevDashTheme {
        ProjectScreenContent(
            projectState = projectState.value,
            onClick = { },
            onSwipeToDelete = { id ->
                val updatedList = projectState.value.projects.filter { it.id != id }
                projectState.value = projectState.value.copy(projects = updatedList)
                true
            },
            onSwipeToPin = { _ -> },
            onLongPress = { }
        )
    }
}