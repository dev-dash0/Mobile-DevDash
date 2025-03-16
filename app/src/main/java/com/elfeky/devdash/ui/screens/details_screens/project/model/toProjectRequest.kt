package com.elfeky.devdash.ui.screens.details_screens.project.model

import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest

fun Project.toProjectRequest(): ProjectRequest {
    return ProjectRequest(
        name = name,
        description = description,
        creationDate = creationDate,
        startDate = startDate,
        endDate = endDate,
        priority = priority,
        status = status
    )
}
