package com.elfeky.devdash.ui.common.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel
import com.elfeky.devdash.ui.common.dialogs.project.model.CompanyUiModel
import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuUiModel
import com.elfeky.devdash.ui.theme.Blue
import com.elfeky.devdash.ui.theme.Gray
import com.elfeky.devdash.ui.theme.Orange
import com.elfeky.devdash.ui.theme.Red
import com.elfeky.devdash.ui.theme.White

val statusList = listOf(
    MenuUiModel(Icons.Default.Circle, Color(0xFFC2BFBF), "Backlog"),
    MenuUiModel(Icons.Default.Circle, Color(0xFF8E8E8E), "To Do"),
    MenuUiModel(Icons.Default.Circle, Color(0xFF4854F1), "In Progress"),
    MenuUiModel(Icons.Default.Circle, Color(0xFFFFA500), "Reviewing"),
    MenuUiModel(Icons.Default.Circle, Color(0xFF1E8024), "Completed"),
    MenuUiModel(Icons.Default.Circle, Color(0xFFD32F2F), "Canceled"),
    MenuUiModel(Icons.Default.Circle, Color(0xFFFFC107), "Postponed")
)

val projectStatusList = listOf(
    MenuUiModel(Icons.Default.Circle, Color(0xFFC2BFBF), "Planning"),
    MenuUiModel(Icons.Default.Circle, Color(0xFF4854F1), "Working on"),
    MenuUiModel(Icons.Default.Circle, Color(0xFFFFA500), "Reviewing"),
    MenuUiModel(Icons.Default.Circle, Color(0xFF1E8024), "Completed"),
    MenuUiModel(Icons.Default.Circle, Color(0xFFD32F2F), "Canceled"),
    MenuUiModel(Icons.Default.Circle, Color(0xFFFFC107), "Postponed")
)

val priorityList = listOf(
    MenuUiModel(Icons.Default.Flag, Gray, "Low"),
    MenuUiModel(Icons.Default.Flag, Blue, "Medium"),
    MenuUiModel(Icons.Default.Flag, Orange, "High"),
    MenuUiModel(Icons.Default.Flag, Red, "Urgent")
)

val typeList = listOf(
    MenuUiModel(Icons.Default.BugReport, Red, "Bug"),
    MenuUiModel(Icons.Default.AutoAwesome, Yellow, "Feature"),
    MenuUiModel(Icons.Default.Checklist, White, "Task")
)

val labelList = mutableListOf(
    "UI", "Test", "Fix", "Mobile", "DB", "Front-end", "Back-end", "Bug", "Issue"
)

val assigneeList = listOf(
    UserUiModel("Mohamed", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    UserUiModel("Amira", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    UserUiModel("Hossam", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    UserUiModel("Youssef", ""),
    UserUiModel("Ahmed", "")
)

val companyList = listOf(
    CompanyUiModel("Google", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    CompanyUiModel("Microsoft", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    CompanyUiModel("Meta", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    CompanyUiModel("OpenAI", ""),
    CompanyUiModel("Intel", "")
)

