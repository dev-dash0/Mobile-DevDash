package com.elfeky.devdash.ui.screens.details_screens.project

import com.elfeky.domain.model.project.Project

data class ProjectState(
    val isLoading: Boolean = false,
    val isCreatingProject: Boolean = false,
    val projects: List<Project> = emptyList(),
    val error: String = "",
    val showCreateDialog: Boolean = false,
    val projectCreationError: String? = null
)
