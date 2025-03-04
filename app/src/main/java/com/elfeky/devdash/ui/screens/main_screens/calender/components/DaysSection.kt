package com.elfeky.devdash.ui.screens.main_screens.calender.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DaysSection(
    modifier: Modifier = Modifier,
    dates: List<String>,
    selectedIndex: MutableIntState
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(dates.size){dateIndex ->
            DayChip(
                date = dates[dateIndex],
                dateIndex = dateIndex,
                selectedIndex = selectedIndex
            )
        }
    }
}