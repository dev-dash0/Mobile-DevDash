package com.elfeky.devdash.ui.screens.main_screens.calender.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.screens.main_screens.calender.CalendarScreenState
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.dashboard.CalenderIssue
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalenderContent(
    modifier: Modifier = Modifier,
    state: CalendarScreenState,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (state.isCalendarLoading) {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else if (state.calendarError.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.calendarError,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        } else {
            stickyHeader {
                val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
                CalendarView(
                    datesWithIndicators = state.calender?.keys?.map {
                        LocalDate.parse(it, dateFormatter)
                    }?.toSet() ?: emptySet(),
                    selectedDate = selectedDate,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(bottom = 16.dp),
                    onDateSelected = onDateSelected
                )
            }

            val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
            val selectedDateIssues = state.calender?.get(selectedDate.format(dateFormatter))

            if (!selectedDateIssues.isNullOrEmpty()) {
                items(selectedDateIssues) { issue ->
                    CalenderIssueChip(
                        calenderIssue = issue,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            } else {
                item {
                    Text(
                        text = "No issues for this date.",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CalenderContentPreview() {
    DevDashTheme {
        val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        val mockCalendarData = mapOf(
            LocalDate.now().minusDays(2).format(dateFormatter) to listOf(
                CalenderIssue(
                    1,
                    "Fix login bug",
                    "High",
                    "Bug",
                    "2025-07-10",
                    "2025-07-15",
                    "Project A",
                    "Tenant X"
                )
            ),
            LocalDate.now().format(dateFormatter) to listOf(
                CalenderIssue(
                    2,
                    "Implement feature X",
                    "Medium",
                    "Feature",
                    "2025-07-12",
                    "2025-07-20",
                    "Project B",
                    "Tenant Y"
                ),
                CalenderIssue(
                    3,
                    "Update UI components",
                    "Low",
                    "Task",
                    "2025-07-13",
                    "2025-07-14",
                    "Project A",
                    "Tenant X"
                )
            ),
            LocalDate.now().plusDays(1).format(dateFormatter) to listOf(
                CalenderIssue(
                    4,
                    "Team meeting",
                    "N/A",
                    "Meeting",
                    "2025-07-15",
                    "2025-07-15",
                    "Project C",
                    "Tenant Z"
                )
            )
        )
        val mockState = CalendarScreenState(
            calender = mockCalendarData,
            isCalendarLoading = false,
            calendarError = ""
        )
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }

        CalenderContent(
            state = mockState,
            selectedDate = selectedDate,
            onDateSelected = { newDate -> selectedDate = newDate }
        )
    }
}