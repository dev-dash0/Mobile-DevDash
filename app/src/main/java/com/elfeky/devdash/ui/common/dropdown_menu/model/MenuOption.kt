package com.elfeky.devdash.ui.common.dropdown_menu.model

import androidx.annotation.FloatRange
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority.Companion.priorityList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.issueStatusList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.projectStatusList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type.Companion.typeList

interface MenuOption {
    val icon: ImageVector
    val color: Color
    val text: String
}

enum class Priority(
    override val icon: ImageVector,
    override val color: Color,
    override val text: String
) : MenuOption {
    Low(Icons.Outlined.Flag, Color.Gray, "Low"),
    Medium(Icons.Outlined.Flag, Color.Blue, "Medium"),
    High(Icons.Outlined.Flag, Color(0xFFCC5210), "High"),
    Urgent(Icons.Outlined.Flag, Color.Red, "Urgent");

    companion object {
        val priorityList = entries
    }
}

enum class Status(
    override val icon: ImageVector,
    override val color: Color,
    override val text: String,
    @FloatRange(from = 0.0, to = 1.0) val percentage: Float? = null
) : MenuOption {
    Backlog(Icons.Default.Circle, Color(0xFFC2BFBF), "BackLog", 0f),
    ToDo(Icons.Default.Circle, Color(0xFF8E8E8E), "To do", 0.25f),
    InProgress(Icons.Default.Circle, Color(0xFF4854F1), "In Progress", 0.5f),
    Planning(Icons.Default.Circle, Color(0xFFC2BFBF), "Planning", 0.25f),
    Planned(Icons.Default.Circle, Color(0xFFC2BFBF), "Planned", 0.25f),
    WorkingOn(Icons.Default.Circle, Color(0xFF4854F1), "Working on", 0.5f),
    Reviewing(Icons.Default.Circle, Color(0xFFFFA500), "Reviewing", 0.75f),
    Completed(Icons.Default.Circle, Color(0xFF1E8024), "Completed"),
    Canceled(Icons.Default.Circle, Color(0xFFD32F2F), "Canceled"),
    Postponed(Icons.Default.Circle, Color(0xFFFFC107), "Postponed");

    companion object {
        val issueStatusList =
            listOf(Backlog, ToDo, InProgress, Reviewing, Completed, Canceled, Postponed)
        val projectStatusList =
            listOf(Planning, WorkingOn, Reviewing, Completed, Canceled, Postponed)
        val sprintStatusList = listOf(Planned, InProgress, Completed)
    }
}

enum class Type(
    override val icon: ImageVector,
    override val color: Color,
    override val text: String
) : MenuOption {
    Feature(Icons.Default.AutoAwesome, Color.Yellow, "Feature"),
    Task(Icons.Default.Checklist, Color.White, "Task"),
    Bug(Icons.Default.BugReport, Color.Red, "Bug");

    companion object {
        val typeList = entries
    }
}

fun String?.toPriority(): Priority = priorityList.find { it.text == this } ?: priorityList[0]

fun String?.toIssueStatus(): Status = issueStatusList.find { it.text == this } ?: issueStatusList[0]

fun String?.toProjectStatus(): Status =
    projectStatusList.find { it.text == this } ?: projectStatusList[0]

fun String?.toType(): Type = typeList.find { it.text == this } ?: typeList[0]
