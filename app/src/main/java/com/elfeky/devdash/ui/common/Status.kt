package com.elfeky.devdash.ui.common

import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.elfeky.devdash.R

sealed class Status(
    @StringRes val status: Int,
    @FloatRange(.0, 1.0) val percentage: Float?,
    val color: Color
) {
    data object Backlog : Status(R.string.backlog, 0f, Color(0xFFC2BFBF))
    data object ToDo : Status(R.string.to_do, 0.25f, Color(0xFF8E8E8E))
    data object InProgress : Status(R.string.in_progress, .5f, Color(0xFF4854F1))
    data object Reviewing : Status(R.string.reviewing, .75f, Color(0xFFFFA500))
    data object Completed : Status(R.string.completed, null, Color(0xFF1E8024))
    data object Canceled : Status(R.string.canceled, null, Color(0xFFD32F2F))
    data object Postponed : Status(R.string.postponed, null, Color(0xFFFFC107))
}

fun String.toStatus(): Status {
    return when (this) {
        "Backlog" -> Status.Backlog
        "To Do" -> Status.ToDo
        "In Progress" -> Status.InProgress
        "Reviewing" -> Status.Reviewing
        "Completed" -> Status.Completed
        "Canceled" -> Status.Canceled
        "Postponed" -> Status.Postponed
        else -> Status.Backlog
    }
}