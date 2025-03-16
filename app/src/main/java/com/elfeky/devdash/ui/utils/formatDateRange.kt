package com.elfeky.devdash.ui.utils

import java.time.Instant
import java.time.ZoneId

fun formatDateRange(startDate: Long?, endDate: Long?): String {
    val startYear = startDate?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).year }
    val endYear = endDate?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).year }
    val start =
        startDate?.toStringDate(pattern = if (startYear == endYear) "dd MMM" else "dd MMM yyyy")
            ?: ""
    val end = endDate?.toStringDate(pattern = "dd MMM yyyy") ?: ""

    return if (startDate != null && endDate != null) "$start - $end" else ""
}