package com.elfeky.devdash.ui.screens.details_screens.project

import com.elfeky.domain.model.project.Project

data class ProjectState(
    val projects: List<Project> = emptyList(),
    val pinnedProjects: List<Project> = emptyList(),
    val error: String = "",
    val selectedProject: Project? = null,
    val showProjectDialog: Boolean = false,
    val projectDeleted: Boolean = false
)
