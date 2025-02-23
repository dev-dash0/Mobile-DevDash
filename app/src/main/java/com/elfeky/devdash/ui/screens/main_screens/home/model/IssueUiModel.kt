package com.elfeky.devdash.ui.screens.main_screens.home.model

import androidx.compose.ui.graphics.Color
import com.elfeky.devdash.ui.common.Status
import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel

data class IssueUiModel(
    val projectName: String,
    val date: String,
    val status: Status,
    val issueTitle: String,
    val labels: List<String>,
    val assignees: List<UserUiModel>,
    val priorityTint: Color,
)
