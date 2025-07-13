package com.elfeky.devdash.ui.common.dialogs.issue.model

import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type
import com.elfeky.domain.model.account.UserProfile

data class IssueUiModel(
    val title: String,
    val description: String?,
    val labels: String,
    val startDate: Long?,
    val deadline: Long?,
    val type: Type,
    val priority: Priority,
    val status: Status,
    val assignedUsers: List<UserProfile>
)
