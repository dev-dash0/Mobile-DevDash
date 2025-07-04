package com.elfeky.devdash.ui.common.dialogs.issue.model

import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuOption
import com.elfeky.domain.model.account.UserProfile

data class IssueUiModel(
    val title: String,
    val description: String?,
    val labels: String,
    val startDate: Long?,
    val deadline: Long?,
    val type: String,
    val priority: MenuOption,
    val status: String,
    val assignedUsers: List<UserProfile>
)
