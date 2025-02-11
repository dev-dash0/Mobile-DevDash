package com.elfeky.devdash.ui.common.component

import com.elfeky.devdash.ui.utils.toStringDate

fun formatDateRange(startDate: Long?, endDate: Long?): String {
    val start = startDate?.toStringDate() ?: ""
    val end = endDate?.toStringDate() ?: ""
    return "$start - $end"
}