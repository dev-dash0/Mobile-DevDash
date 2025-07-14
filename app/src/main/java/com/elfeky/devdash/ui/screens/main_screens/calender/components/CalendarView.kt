package com.elfeky.devdash.ui.screens.main_screens.calender.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.DevDashTheme
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    datesWithIndicators: Set<LocalDate>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val calendarDays = remember(selectedDate.year) {
        val startDate = selectedDate.withDayOfYear(1).minusYears(1)
        val endDate = selectedDate.withDayOfYear(1).plusYears(1).withDayOfYear(365)
        val days = mutableListOf<LocalDate>()
        var currentDate = startDate
        while (!currentDate.isAfter(endDate)) {
            days.add(currentDate)
            currentDate = currentDate.plusDays(1)
        }
        days
    }

    val scrollToDate: (LocalDate) -> Unit = { date ->
        val index = ChronoUnit.DAYS.between(calendarDays.first(), date).toInt()
        if (index >= 0) {
            coroutineScope.launch {
                val centerOffset =
                    (listState.layoutInfo.viewportEndOffset / 2) - (64.dp.value / 2)
                listState.animateScrollToItem(
                    index.coerceAtLeast(0),
                    scrollOffset = -centerOffset.toInt()
                )
            }
        }
    }

    LaunchedEffect(selectedDate) {
        scrollToDate(selectedDate)
    }

    val monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.getDefault())
    val yearFormatter = DateTimeFormatter.ofPattern("yyyy", Locale.getDefault())

    Column(modifier = modifier) {
        CalendarHeader(
            month = selectedDate.format(monthFormatter),
            year = selectedDate.format(yearFormatter),
            onPreviousMonthClick = { onDateSelected(selectedDate.minusMonths(1)) },
            onNextMonthClick = { onDateSelected(selectedDate.plusMonths(1)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(items = calendarDays, key = { it.toEpochDay() }) { date ->
                DayOfWeekItem(
                    day = DayState(
                        date = date,
                        isSelected = date.isEqual(selectedDate),
                        hasIndicator = datesWithIndicators.contains(date)
                    ),
                    onDayClick = { onDateSelected(it) }
                )
            }
        }
    }
}

@Preview
@Composable
fun CalendarViewPreview() {
    DevDashTheme {
        CalendarView(
            datesWithIndicators = setOf(LocalDate.now().plusDays(2), LocalDate.now().minusDays(3)),
            selectedDate = LocalDate.now(),
            onDateSelected = {}
        )
    }
}
