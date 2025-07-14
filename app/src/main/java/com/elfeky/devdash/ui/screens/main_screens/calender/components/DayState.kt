package com.elfeky.devdash.ui.screens.main_screens.calender.components

import java.time.LocalDate

data class DayState(
    val date: LocalDate,
    val isSelected: Boolean,
    val hasIndicator: Boolean
)
