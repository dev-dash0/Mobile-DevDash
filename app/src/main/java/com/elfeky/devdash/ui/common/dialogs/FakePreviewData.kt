package com.elfeky.devdash.ui.common.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import com.elfeky.devdash.ui.common.dialogs.model.MenuDataModel
import com.elfeky.devdash.ui.common.dialogs.model.User
import com.elfeky.devdash.ui.theme.Blue
import com.elfeky.devdash.ui.theme.Gray
import com.elfeky.devdash.ui.theme.Red
import com.elfeky.devdash.ui.theme.White

// Status List
val statusList = listOf(
    MenuDataModel(Icons.Default.Circle, Color(0xFFC2BFBF), "Backlog"),
    MenuDataModel(Icons.Default.Circle, Color(0xFF8E8E8E), "To Do"),
    MenuDataModel(Icons.Default.Circle, Color(0xFF4854F1), "In Progress"),
    MenuDataModel(Icons.Default.Circle, Color(0xFFFFA500), "Reviewing"),
    MenuDataModel(Icons.Default.Circle, Color(0xFF1E8024), "Completed"),
    MenuDataModel(Icons.Default.Circle, Color(0xFFD32F2F), "Canceled"),
    MenuDataModel(Icons.Default.Circle, Color(0xFFFFC107), "Postponed")
)

// Priority List
val priorityList = listOf(
    MenuDataModel(Icons.Default.Flag, Gray, "Low"),
    MenuDataModel(Icons.Default.Flag, Blue, "Medium"),
    MenuDataModel(Icons.Default.Flag, Color(0xFFFFA500), "High"),
    MenuDataModel(Icons.Default.Flag, Red, "Urgent")
)

// Type List
val typeList = listOf(
    MenuDataModel(Icons.Default.BugReport, Red, "Bug"),
    MenuDataModel(Icons.Default.AutoAwesome, Yellow, "Feature"),
    MenuDataModel(Icons.Default.Checklist, White, "Task")
)

// Label List
val labelList = mutableListOf(
    "UI", "Test", "Fix", "Mobile", "DB", "Front-end", "Back-end", "Bug", "Issue"
)

// Assignee List
val assigneeList = listOf(
    User("Mohamed", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    User("Amira", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    User("Hossam", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    User("Youssef", ""),
    User("Ahmed", "")
)

