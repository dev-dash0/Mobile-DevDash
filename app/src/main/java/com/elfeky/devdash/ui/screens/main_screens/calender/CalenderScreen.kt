package com.elfeky.devdash.ui.screens.main_screens.calender

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.screens.main_screens.calender.components.CalenderContent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalenderScreen(
    modifier: Modifier = Modifier,
    viewModel: CalenderViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(state.calender) {
        val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        if (state.calender != null && state.calender.isNotEmpty()) {
            val currentSelectedDateIssues = state.calender[selectedDate.format(dateFormatter)]
            if (currentSelectedDateIssues.isNullOrEmpty()) {
                val firstDateWithIssues = state.calender.keys.firstOrNull { key ->
                    !state.calender[key].isNullOrEmpty()
                }?.let { key -> LocalDate.parse(key, dateFormatter) }
                selectedDate = firstDateWithIssues
                    ?: selectedDate
            }
        } else if (!state.isCalendarLoading && state.calendarError.isEmpty()) {
            selectedDate = LocalDate.now()
        }
    }

    CalenderContent(
        modifier = modifier,
        state = state,
        selectedDate = selectedDate,
        onDateSelected = { newDate -> selectedDate = newDate }
    )
}