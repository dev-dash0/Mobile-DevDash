package com.elfeky.devdash.ui.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toStringDate(): String =
    Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(DateTimeFormatter.ofPattern("MMM dd", Locale.getDefault()))