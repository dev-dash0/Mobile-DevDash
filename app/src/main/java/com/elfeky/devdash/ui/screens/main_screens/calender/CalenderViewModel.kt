package com.elfeky.devdash.ui.screens.main_screens.calender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.usecase.dashboard.GetCalendarUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CalenderViewModel @Inject constructor(
    private val getCalendarUseCase: GetCalendarUseCase
) : ViewModel() {
    var state by mutableStateOf(CalendarScreenState(isCalendarLoading = true))
        private set

    init {
        getCalendar()
    }

    private fun getCalendar() {
        getCalendarUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    state = state.copy(isCalendarLoading = true)
                }

                is Resource.Success -> {
                    val calendarMap = result.data?.result?.associate { calendarDay ->
                        calendarDay.date to calendarDay.issues
                    }
                    state = state.copy(
                        calender = calendarMap,
                        isCalendarLoading = false,
                        calendarError = ""
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        calendarError = result.message ?: "An unexpected error occurred",
                        isCalendarLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}