package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.compose.runtime.Immutable
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.sprint.Sprint

@Immutable
data class ProjectDetailsUiState(
    val project: Project? = null,
    val isPinned: Boolean = false,
    val isLoading: Boolean = false,
    val pinnedIssues: List<Issue> = emptyList(),
    val pinnedSprints: List<Sprint> = emptyList(),
    val dialogType: ProjectDetailsReducer.DialogType? = null,
    val onEvent: (ProjectDetailsReducer.Event) -> Unit // Callback to send events to ViewModel
)
