package com.elfeky.devdash.ui.screens.main_screens.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import com.elfeky.devdash.ui.common.Status
import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel
import com.elfeky.devdash.ui.screens.main_screens.home.components.CircularProgressCardUiModel
import com.elfeky.devdash.ui.screens.main_screens.home.model.IssueUiModel
import com.elfeky.devdash.ui.theme.Orange
import com.elfeky.devdash.ui.theme.Red

val issueList = listOf(
    IssueUiModel(
        projectName = "DevDash",
        date = "12 Feb | 18 Feb",
        status = Status.ToDo,
        issueTitle = "Implement User Authentication",
        labels = listOf("Feature", "Authentication", "Backend"),
        assignees = listOf(
            UserUiModel("John Doe", "john.doe@example.com"),
            UserUiModel("Jane Smith", "jane.smith@example.com")
        ),
        priorityTint = Red
    ),
    IssueUiModel(
        projectName = "Project X",
        date = "15 Feb | 22 Feb",
        status = Status.InProgress,
        issueTitle = "Fix UI Bug on Login Screen",
        labels = listOf("Bug", "UI", "Frontend"),
        assignees = listOf(
            UserUiModel("Alice Johnson", "alice.johnson@example.com")
        ),
        priorityTint = Orange
    ),
    IssueUiModel(
        projectName = "Mobile App",
        date = "20 Feb | 28 Feb",
        status = Status.Reviewing,
        issueTitle = "Optimize Database Queries",
        labels = listOf("Performance", "Database", "Backend"),
        assignees = listOf(
            UserUiModel("Bob Williams", "bob.williams@example.com"),
            UserUiModel("Eve Brown", "eve.brown@example.com")
        ),
        priorityTint = Green
    ),
    IssueUiModel(
        projectName = "Website Redesign",
        date = "25 Feb | 05 Mar",
        status = Status.Canceled,
        issueTitle = "Update Landing Page Design",
        labels = listOf("Design", "UI", "Frontend"),
        assignees = listOf(
            UserUiModel("Charlie Davis", "charlie.davis@example.com")
        ),
        priorityTint = Yellow
    ),
    IssueUiModel(
        projectName = "DevDash",
        date = "01 Mar | 08 Mar",
        status = Status.InProgress,
        issueTitle = "Add Dark Mode Support",
        labels = listOf("Feature", "UI", "Frontend"),
        assignees = listOf(
            UserUiModel("David Wilson", "david.wilson@example.com"),
            UserUiModel("Fiona Garcia", "fiona.garcia@example.com")
        ),
        priorityTint = Red
    ),
    IssueUiModel(
        projectName = "Project X",
        date = "05 Mar | 12 Mar",
        status = Status.InProgress,
        issueTitle = "Refactor Codebase",
        labels = listOf("Refactoring", "Code Quality", "Backend"),
        assignees = listOf(
            UserUiModel("George Rodriguez", "george.rodriguez@example.com")
        ),
        priorityTint = Orange
    ),
    IssueUiModel(
        projectName = "Mobile App",
        date = "10 Mar | 17 Mar",
        status = Status.Completed,
        issueTitle = "Implement Push Notifications",
        labels = listOf("Feature", "Notifications", "Mobile"),
        assignees = listOf(
            UserUiModel("Hannah Martinez", "hannah.martinez@example.com"),
            UserUiModel("Ian Anderson", "ian.anderson@example.com")
        ),
        priorityTint = Green
    ),
    IssueUiModel(
        projectName = "Website Redesign",
        date = "15 Mar | 22 Mar",
        status = Status.Canceled,
        issueTitle = "Improve SEO",
        labels = listOf("SEO", "Marketing", "Website"),
        assignees = listOf(
            UserUiModel("Julia Thomas", "julia.thomas@example.com")
        ),
        priorityTint = Yellow
    ),
    IssueUiModel(
        projectName = "DevDash",
        date = "20 Mar | 27 Mar",
        status = Status.Backlog,
        issueTitle = "Add Unit Tests",
        labels = listOf("Testing", "Code Quality", "Backend"),
        assignees = listOf(
            UserUiModel("Kevin Jackson", "kevin.jackson@example.com"),
            UserUiModel("Laura White", "laura.white@example.com")
        ),
        priorityTint = Red
    ),
    IssueUiModel(
        projectName = "Project X",
        date = "25 Mar | 01 Apr",
        status = Status.InProgress,
        issueTitle = "Implement Analytics",
        labels = listOf("Feature", "Analytics", "Backend"),
        assignees = listOf(
            UserUiModel("Michael Harris", "michael.harris@example.com")
        ),
        priorityTint = Orange
    )
)


val progressCards = listOf(
    CircularProgressCardUiModel(
        title = "Total Projects",
        progress = 0.5f,
        colors = listOf(Color.Blue, Color.Cyan)
    ),
    CircularProgressCardUiModel(
        title = "Total Issues Completed",
        progress = 0.75f,
        colors = listOf(Color.Cyan, Color.Blue)
    ),
    CircularProgressCardUiModel(
        title = "Total Issues In Progress",
        progress = 0.25f,
        colors = listOf(Color.Magenta, Color.Blue)
    ),
    CircularProgressCardUiModel(
        title = "Total Issues Overdue",
        progress = 0.75f,
        colors = listOf(Color.Red, Color.Gray)
    )
)