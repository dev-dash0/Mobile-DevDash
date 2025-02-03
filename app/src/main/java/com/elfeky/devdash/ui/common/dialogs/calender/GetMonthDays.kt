package com.elfeky.devdash.ui.common.dialogs.calender

import java.time.DayOfWeek
import java.time.LocalDate

fun getMonthDays(date: LocalDate): List<LocalDate> {
    val firstDayOfMonth = date.withDayOfMonth(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek
    val daysInMonth = date.lengthOfMonth()
    val daysBeforeMonth = when (firstDayOfWeek) {
        DayOfWeek.MONDAY -> 0
        DayOfWeek.TUESDAY -> 1
        DayOfWeek.WEDNESDAY -> 2
        DayOfWeek.THURSDAY -> 3
        DayOfWeek.FRIDAY -> 4
        DayOfWeek.SATURDAY -> 5
        DayOfWeek.SUNDAY -> 6
        else -> 0
    }
    val days = mutableListOf<LocalDate>()
    val previousMonth = firstDayOfMonth.minusMonths(1)
    val daysInPreviousMonth = previousMonth.lengthOfMonth()
    for (i in 1..daysBeforeMonth) {
        days.add(previousMonth.withDayOfMonth(daysInPreviousMonth - daysBeforeMonth + i))
    }
    for (i in 1..daysInMonth) {
        days.add(firstDayOfMonth.withDayOfMonth(i))
    }
    return days
}