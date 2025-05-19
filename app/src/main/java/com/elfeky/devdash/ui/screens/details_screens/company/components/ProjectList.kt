package com.elfeky.devdash.ui.screens.details_screens.company.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.card.ProjectCard
import com.elfeky.devdash.ui.common.component.SwipeToDismissItem
import com.elfeky.devdash.ui.common.dropdown_menu.model.toProjectStatus
import com.elfeky.domain.model.project.Project

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectList(
    projects: List<Project>,
    pinnedProjects: List<Project>,
    scrollBehavior: TopAppBarScrollBehavior,
    onProjectClick: (id: Int) -> Unit,
    onProjectSwipeToDelete: (id: Int) -> Unit,
    onProjectSwipeToPin: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = projects, key = { it.id }) { project ->
            val isPinned by remember(pinnedProjects, project) {
                derivedStateOf {
                    pinnedProjects.contains(project)
                }
            }

            SwipeToDismissItem(
                isPinned = isPinned,
                onSwipeToPin = { onProjectSwipeToPin(project.id) },
                onSwipeToDelete = { onProjectSwipeToDelete(project.id) }
            ) {
                ProjectCard(
                    date = project.endDate,
                    status = project.status.toProjectStatus(),
                    issueTitle = project.name,
                    description = project.description,
                    onClick = { onProjectClick(project.id) },
                    onLongClick = { }
                )
            }
        }
    }
}