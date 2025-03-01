package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.Status
import com.elfeky.devdash.ui.common.card.ProjectCard
import com.elfeky.devdash.ui.screens.details_screens.project.model.ProjectUiModel
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.gradientBackground

@Composable
fun ProjectScreen(
    id: Int,
    projectList: List<ProjectUiModel>,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Company ID : $id", color = MaterialTheme.colorScheme.onBackground)
        }
        items(projectList) { project ->
            ProjectCard(
                project.id,
                project.companyName,
                project.date,
                project.status,
                project.title,
                project.description,
                { id -> onClick(id) }
            )
        }
    }
}

@Preview
@Composable
private fun ProjectScreenPreview() {
    DevDashTheme {
        ProjectScreen(
            id = 0,
            projectList = listOf(
                ProjectUiModel(
                    0,
                    "Google",
                    "15 Nov | 28 Nov",
                    Status.ToDo,
                    "Gemini",
                    "Enhance Gemini"
                )
            ),
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
        ) {}
    }
}