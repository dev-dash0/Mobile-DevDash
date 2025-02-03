package com.elfeky.devdash.ui.common.dialogs.model

import androidx.compose.ui.graphics.Color
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.theme.Blue
import com.elfeky.devdash.ui.theme.Gray
import com.elfeky.devdash.ui.theme.Orange
import com.elfeky.devdash.ui.theme.Red

val statusList = listOf(
    DropDownMenuDataModel(
        icon = R.drawable.ic_todo,
        tint = Color(0xFF908F8F),
        text = "ToDo",
    ),
    DropDownMenuDataModel(
        icon = R.drawable.ic_in_progress,
        tint = Color(0xFF4854F1),
        text = "InProgress",
    ),
    DropDownMenuDataModel(
        icon = R.drawable.ic_completed,
        tint = Color(0xFF1E8024),
        text = "Done",
    ),
)

val priorityList = listOf(
    DropDownMenuDataModel(
        icon = R.drawable.ic_flag,
        tint = Gray,
        text = "Low"
    ),
    DropDownMenuDataModel(
        icon = R.drawable.ic_flag,
        tint = Blue,
        text = "Normal"
    ),
    DropDownMenuDataModel(
        icon = R.drawable.ic_flag,
        tint = Orange,
        text = "High"
    ),
    DropDownMenuDataModel(
        icon = R.drawable.ic_flag,
        tint = Red,
        text = "Urgent"
    ),
)

val labelList = mutableListOf(
    "UI",
    "Test",
    "Fix",
    "Mobile",
    "DB",
    "Front-end",
    "Back-end",
    "Bug",
    "Issue"
)

val assigneeList = listOf(
    "Mohamed" to "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png",
    "Amira" to "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png",
    "Hossam" to "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png",
    "Youssef" to "",
    "Ahmed" to ""
)