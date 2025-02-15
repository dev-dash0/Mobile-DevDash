package com.elfeky.devdash.ui.common.dialogs.issue.model

import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuUiModel

data class IssueUiModel(
    val title: String,
    val description: String?,
    val labels: MutableList<String>,
    val startDate: Long?,
    val deadline: Long?,
    val type: String,
    val priority: MenuUiModel,
    val status: String
)
