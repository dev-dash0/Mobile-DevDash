package com.elfeky.devdash.ui.screens.main_screens.calender

import androidx.lifecycle.ViewModel
import com.elfeky.domain.model.CalenderIssue
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CalenderViewModel @Inject constructor() : ViewModel() {


    val dates = listOf("2025-03-04", "2025-03-05", "2025-03-06")
    val issues: List<List<CalenderIssue>> = listOf(
        listOf(
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),

            ),
        listOf(
            CalenderIssue(
                title = "Task2",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),
            CalenderIssue(
                title = "Task2",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),
            CalenderIssue(
                title = "Task2",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),

            ),
        listOf(
            CalenderIssue(
                title = "Task3",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),
            CalenderIssue(
                title = "Task3",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),
            CalenderIssue(
                title = "Task3",
                priority = "High",
                startDate = "2025-03-01",
                deadline = "2025-03-07",
                projectName = "web",
                tenantName = "Face"
            ),

            )
    )


    fun getDayOfWeek(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)
        return date.dayOfWeek.toString().substring(0, 3)
    }

    fun formatDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("d MMM")
        return date.format(outputFormatter)
    }

}