package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.card.ProjectCard
import com.elfeky.devdash.ui.common.toStatus

@Composable
fun ProjectScreenContent(
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit,
    projectState: ProjectState
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (projectState.isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

            }
        } else {
            items(projectState.projects) { project ->
                ProjectCard(
                    project.id,
                    project.endDate,
                    project.status.toStatus(),
                    project.name,
                    project.description,
                    { id -> onClick(id) }
                )
            }
        }
    }
}