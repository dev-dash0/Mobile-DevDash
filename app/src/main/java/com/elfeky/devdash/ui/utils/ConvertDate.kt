package com.elfeky.devdash.ui.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun Long.toStringDate(): String =
    Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(dateFormatter)

fun Long.toStringDate(pattern: String): String =
    Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(DateTimeFormatter.ofPattern(pattern))


fun String.toEpochMillis(): Long? {
    return runCatching {
        LocalDate.parse(this, dateFormatter)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }.getOrNull()
}

fun nowLocalDate(): String =
    LocalDate
        .now()
        .format(dateFormatter)
