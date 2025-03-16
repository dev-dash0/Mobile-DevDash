package com.elfeky.devdash.ui.common.dialogs.project.model

data class ProjectUiModel(
    val title: String,
    val description: String?,
    val startDate: Long?,
    val endDate: Long?,
    val status: String
)
