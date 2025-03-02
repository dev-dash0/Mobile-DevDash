package com.elfeky.devdash.ui.common.time_line_calender.model

/**
 * Represents a section within a calendar, grouping related events.
 *
 * A `CalendarSection` is a logical grouping of `CalendarEvent` objects.
 * It typically represents a specific category, type, or time-frame of events
 * within a larger calendar view. For example, a section could be named
 * "Meetings", "Holidays", "Personal Events", or "Morning".
 *
 * @property name The name of this calendar section. This is a human-readable
 *                 string that describes the grouping, such as "Meetings" or
 *                 "Vacation". It should be unique within a given calendar view if
 *                 distinct sections are required.
 * @property events The list of `CalendarEvent` objects that belong to this section.
 *                  This list can be empty if there are currently no events in the section.
 */
data class CalendarSection(
    val name: String,
    val events: List<CalendarEvent>
)