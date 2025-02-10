package com.elfeky.devdash.ui.common.dialogs.model

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
class ValidRangeSelectableDates(private val validStartDate: Long) : SelectableDates {

    override fun isSelectableDate(utcTimeMillis: Long): Boolean = utcTimeMillis >= validStartDate

    companion object {
        fun startingFromCurrentDay(): ValidRangeSelectableDates {
            val currentDayStart = LocalDate.now()
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli()
            return ValidRangeSelectableDates(currentDayStart)
        }
    }
}