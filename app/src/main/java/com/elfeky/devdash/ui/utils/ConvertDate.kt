package com.elfeky.devdash.ui.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

fun localDateToString(date: LocalDate): String =
    date.format(DateTimeFormatter.ofPattern("MMM dd", Locale.getDefault()))

fun localDateToLong(date: LocalDate): Long =
    date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

fun longToLocalDate(longValue: Long): LocalDate =
    Instant.ofEpochMilli(longValue).atZone(ZoneOffset.UTC).toLocalDate()