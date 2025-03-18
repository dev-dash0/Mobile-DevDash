package com.elfeky.devdash.ui.screens.main_screens.calender

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.model.dashboard.CalenderIssue
import com.elfeky.domain.usecase.dashboard.GetCalendarUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class CalenderViewModel @Inject constructor(
    private val getCalendarUseCase: GetCalendarUseCase
) : ViewModel() {

//    init {
//        getCalendar()
//    }

    var state = mutableStateOf(CalendarScreenState())
        private set


    val dates = listOf("2025-03-04", "2025-03-05", "2025-03-06")
    val issues: List<List<CalenderIssue>> = listOf(
        listOf(
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),
            CalenderIssue(
                title = "Task1",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),

            ),
        listOf(
            CalenderIssue(
                title = "Task2",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),
            CalenderIssue(
                title = "Task2",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),
            CalenderIssue(
                title = "Task2",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),

            ),
        listOf(
            CalenderIssue(
                title = "Task3",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),
            CalenderIssue(
                title = "Task3",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),
            CalenderIssue(
                title = "Task3",
                priority = "High",
                startDate = "2025-03-01T00:00:00",
                deadline = "2025-03-07T00:00:00",
                projectName = "web",
                tenantName = "Face",
                id = 0,
                type = "Epic"
            ),

            )
    )


    fun getCalendar() {
        getCalendarUseCase().onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    state.value = CalendarScreenState(isCalendarLoading = true)
                }

                is Resource.Success -> {
//                    state.value =
//                        CalendarScreenState(calenderList = result.data?.calendarList, isCalendarLoading = false)
                    Log.i("calendarList", result.data.toString())
                }

                is Resource.Error -> {
                    state.value =
                        CalendarScreenState(
                            calendarError = result.message ?: "An unexpected error is occurred ",
                            isCalendarLoading = false
                        )
                    Log.i("calendarList", result.message ?: "Error" )
                }
            }

        }.launchIn(viewModelScope)

    }


    fun getDayOfWeek(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)
        return date.dayOfWeek.toString().substring(0, 3)
    }

    fun formatDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val date = LocalDate.parse(dateString, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("d MMM")
        return date.format(outputFormatter)
    }

}