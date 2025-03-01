package com.elfeky.devdash.ui.screens.details_screens.project.model

import com.elfeky.devdash.ui.common.Status

data class ProjectUiModel(
    val id: Int,
    val companyName: String,
    val date: String,
    val status: Status,
    val title: String,
    val description: String
)
