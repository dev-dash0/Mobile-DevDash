package com.elfeky.devdash.ui.common.time_line_calender

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.time_line_calender.components.CalendarSectionRow
import com.elfeky.devdash.ui.common.time_line_calender.components.DayDividers
import com.elfeky.devdash.ui.common.time_line_calender.components.DaysRow
import com.elfeky.devdash.ui.common.time_line_calender.components.HoursRow
import com.elfeky.devdash.ui.common.time_line_calender.model.CalendarSection
import com.elfeky.devdash.ui.common.time_line_calender.model.ScheduleCalendarState
import java.time.LocalDateTime


/**
 * Displays a scrollable calendar view that shows a schedule for a given time span.
 *
 * This composable arranges days and hours in a horizontal layout, allowing users to
 * navigate through time. It also displays sections, hour dividers, and a "now" indicator.
 *
 * @param state The state object that holds the calendar's current view and scroll information.
 *              This object is responsible for managing the visible time range and the
 *              horizontal scroll position.
 * @param modifier Modifier for styling and layout customization of the entire calendar.
 * @param viewSpan The total time span (in seconds) to be displayed in the calendar view.
 *                 Defaults to 48 hours (48 * 3600L).
 * @param sections A list of [CalendarSection] objects, each representing a distinct section
 *                 of the schedule. These sections are displayed vertically within the calendar.
 *                 Defaults to an empty list.
 * @param now The current [LocalDateTime] used to display the "now" indicator on the calendar.
 *            Defaults to the current system time.
 * @param eventTimesVisible Whether to display the start and end times of events within each
 *                          calendar section. Defaults to true.
 */
@Composable
fun ScheduleCalendar(
    state: ScheduleCalendarState,
    modifier: Modifier = Modifier,
    viewSpan: Long = 48 * 3600L, // in seconds
    sections: List<CalendarSection> = emptyList(),
    now: LocalDateTime = LocalDateTime.now(),
    eventTimesVisible: Boolean = true
) = BoxWithConstraints(
    modifier
        .fillMaxWidth()
        .scrollable(
            state.scrollableState,
            Orientation.Horizontal,
            flingBehavior = state.scrollFlingBehavior
        )
) {
    state.updateView(viewSpan, constraints.maxWidth)

    Column {
        DaysRow(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        HoursRow(state)

        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                sections.forEach {
                    CalendarSectionRow(
                        section = it,
                        state = state,
                        eventTimesVisible = eventTimesVisible
                    )
                }
            }

            // hour dividers
            Canvas(modifier = Modifier.matchParentSize()) {
                state.visibleHours.forEach { localDateTime ->
                    val offsetPercent = state.offsetFraction(localDateTime)
                    drawLine(
                        color = Color.Gray,
                        strokeWidth = 2f,
                        start = Offset(offsetPercent * size.width, 0f),
                        end = Offset(offsetPercent * size.width, size.height),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(10f, 20f),
                            phase = 5f
                        )
                    )
                }
            }
        }
    }

    DayDividers(
        state = state,
        modifier = Modifier.matchParentSize()
    )

    // "now" indicator
    Canvas(modifier = Modifier.matchParentSize()) {
        val offsetPercent = state.offsetFraction(now)
        drawLine(
            color = Color.Magenta,
            strokeWidth = 6f,
            start = Offset(offsetPercent * size.width, 0f),
            end = Offset(offsetPercent * size.width, size.height)
        )
        drawCircle(
            Color.Magenta,
            center = Offset(offsetPercent * size.width, 12f),
            radius = 12f
        )
    }
}