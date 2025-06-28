package com.elfeky.devdash.ui.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

fun Long.toStringDate(format: DateTimeFormatter = dateFormatter): String =
    Instant
        .ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
        .format(format)

fun Long.toStringDate(pattern: String): String =
    Instant
        .ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
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

fun String.toEpochMillis(format: DateTimeFormatter): Long? {
    return runCatching {
        LocalDateTime.parse(this, format)
            .toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    }.getOrNull()
}

fun nowLocalDate(): String =
    LocalDate
        .now()
        .format(dateFormatter)
