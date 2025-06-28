package com.elfeky.devdash.ui.utils

import java.time.format.DateTimeFormatter

fun formatDisplayDate(date: String, formatter: DateTimeFormatter): String {
    val longDate = date.toEpochMillis(formatter) ?: return date
    return longDate.toStringDate(DateTimeFormatter.ofPattern("d MMM"))
}

fun formatDisplayDate(date: String): String {
    val longDate = date.toEpochMillis() ?: return date
    return longDate.toStringDate(DateTimeFormatter.ofPattern("d MMM"))
}