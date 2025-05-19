package com.elfeky.devdash.ui.common.dialogs.issue.model

import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuOption

data class IssueUiModel(
    val title: String,
    val description: String?,
    val labels: List<String>,
    val startDate: Long?,
    val deadline: Long?,
    val type: String,
    val priority: MenuOption,
    val status: String
)
