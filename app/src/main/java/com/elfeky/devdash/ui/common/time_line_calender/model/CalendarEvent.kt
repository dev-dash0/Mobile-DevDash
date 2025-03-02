package com.elfeky.devdash.ui.common.time_line_calender.model

import androidx.compose.ui.graphics.Color
import com.elfeky.devdash.ui.theme.G500
import java.time.LocalDateTime

/**
 * Represents a single event on a calendar.
 *
 * @property startDate The date and time when the event starts.
 * @property endDate The date and time when the event ends.
 * @property name The name or title of the event. Defaults to an empty string.
 * @property description A more detailed description of the event. Defaults to an empty string.
 * @property color The color to be used when displaying the event on a calendar view. Defaults to [G500].
 *
 * @constructor Creates a new CalendarEvent instance.
 */
data class CalendarEvent(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val name: String = "",
    val description: String = "",
    val color: Color = G500
)