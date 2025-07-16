package com.elfeky.devdash.ui.utils

import java.time.format.DateTimeFormatter

fun formatDisplayDate(date: String, pattern: String = "d MMM"): String {
    val longDate =
        if (date.length == 10) date.toEpochMillis() ?: return date else date.toEpochMillis(
            dateTimeFormatter
        ) ?: return date

    return longDate.toStringDate(DateTimeFormatter.ofPattern(pattern))
}