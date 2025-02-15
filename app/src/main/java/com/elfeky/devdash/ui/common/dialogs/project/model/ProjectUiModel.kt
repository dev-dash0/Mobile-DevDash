package com.elfeky.devdash.ui.common.dialogs.project.model

data class ProjectUiModel(
    val title: String,
    val description: String?,
    val startDate: Long?,
    val deadline: Long?,
    val type: String,
    val status: String
)
