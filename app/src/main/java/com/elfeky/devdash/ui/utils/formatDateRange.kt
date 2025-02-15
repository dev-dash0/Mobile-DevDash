package com.elfeky.devdash.ui.utils

fun formatDateRange(startDate: Long?, endDate: Long?): String {
    val start = startDate?.toStringDate() ?: ""
    val end = endDate?.toStringDate() ?: ""
    return "$start - $end"
}