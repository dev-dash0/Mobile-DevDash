package com.elfeky.devdash.ui.common.dialogs.model

data class IssueDataModel(
    val title: String,
    val description: String?,
    val labels: MutableList<String>,
    val startDate: Long?,
    val deadline: Long?,
    val type: String,
    val priority: MenuDataModel,
    val status: String
)
