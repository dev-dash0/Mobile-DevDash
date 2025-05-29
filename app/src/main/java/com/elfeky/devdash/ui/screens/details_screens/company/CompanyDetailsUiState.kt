package com.elfeky.devdash.ui.screens.details_screens.company

import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.tenant.Tenant

data class CompanyDetailsUiState(
    val tenantId: Int,
    val userId: Int? = null,
    val tenant: Tenant? = null,
    val isPinned: Boolean = false,
    val isLoading: Boolean = true,
    val projectsLoading: Boolean = true,
    val projects: List<Project> = emptyList(),
    val pinnedProjects: List<Project> = emptyList(),
    val isDeleted: Boolean = false,
    val deleteErrorMessage: String? = null,
    val error: String? = null
)
