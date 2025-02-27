package com.elfeky.devdash.ui.screens.nested_nav_screens.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.Status
import com.elfeky.devdash.ui.common.card.ProjectCard
import com.elfeky.devdash.ui.screens.nested_nav_screens.project.model.ProjectUiModel
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.gradientBackground

@Composable
fun ProjectScreen(
    projectList: List<ProjectUiModel>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(projectList) { project ->
            ProjectCard(
                project.companyName,
                project.date,
                project.status,
                project.title,
                project.description,
                onClick
            )
        }
    }
}

@Preview
@Composable
private fun ProjectScreenPreview() {
    DevDashTheme {
        ProjectScreen(
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