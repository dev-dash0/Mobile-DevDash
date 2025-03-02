package com.elfeky.devdash.ui.common.time_line_calender

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.elfeky.devdash.ui.common.time_line_calender.model.ScheduleCalendarState
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * Creates and remembers a [ScheduleCalendarState] for managing the state of a schedule calendar.
 *
 * This function simplifies the creation and management of the state for a calendar that allows the
 * user to select a specific date and potentially time. It utilizes Composes' `remember` API
 * to ensure that the state is preserved across recompositions.
 *
 * @param referenceDateTime The initial date and time that the calendar will use as a reference point.
 *                          Defaults to the current date (with time truncated to the start of the day).
 *                          This usually represents the currently selected day or the day the user
 *                          is viewing.
 * @param onDateTimeSelected A callback function that is invoked when the user selects a new date and/or time
 *                           in the calendar. The selected `LocalDateTime` is passed as an argument to this callback.
 *                           Defaults to an empty lambda (i.e., does nothing).
 * @return A [ScheduleCalendarState] instance that can be used to interact with and observe the state
 *         of the calendar. This instance is remembered across recompositions.
 *
 * Example Usage:
 * ```
 * val calendarState = rememberScheduleCalendarState(
 *     referenceDateTime = LocalDateTime.of(2024, 1, 15, 0, 0),
 *     onDateTimeSelected = { selectedDateTime ->
 *         println("Selected date and time: $selectedDateTime")
 *     }
 * )
 *
 * // Use calendarState in your Composable, for example:
 * // MyCalendar(state = calendarState)
 * ```
 *
 */
@Composable
fun rememberScheduleCalendarState(
    referenceDateTime: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS),
    onDateTimeSelected: (LocalDateTime) -> Unit = {}
): ScheduleCalendarState {
    val coroutineScope = rememberCoroutineScope()
    return remember(coroutineScope) {
        ScheduleCalendarState(
            referenceDateTime = referenceDateTime,
            onDateTimeSelected = onDateTimeSelected,
            coroutineScope = coroutineScope,
        )
    }
}