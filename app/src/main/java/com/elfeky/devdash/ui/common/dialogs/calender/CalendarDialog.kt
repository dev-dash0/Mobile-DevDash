package com.elfeky.devdash.ui.common.dialogs.calender

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.CustomAlertDialog
import com.elfeky.devdash.ui.theme.BlueGray
import com.elfeky.devdash.ui.theme.LightGray
import com.elfeky.devdash.ui.theme.White
import com.elfeky.devdash.ui.utils.localDateToLong
import com.elfeky.devdash.ui.utils.longToLocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDialog(
    dateRangePickerState: DateRangePickerState,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    var selectedStartDate =
        dateRangePickerState.selectedStartDateMillis?.let { longToLocalDate(it) }
    var selectedEndDate = dateRangePickerState.selectedEndDateMillis?.let { longToLocalDate(it) }
    var displayedMonth by remember {
        mutableStateOf(
            selectedStartDate ?: selectedEndDate
            ?: longToLocalDate(dateRangePickerState.displayedMonthMillis)
        )
    }

    var selectedField by remember { mutableStateOf(SelectionField.START) }

    LaunchedEffect(displayedMonth) {
        dateRangePickerState.displayedMonthMillis = localDateToLong(displayedMonth)
    }

    CustomAlertDialog(
        onCancel = onCancel,
        onConfirm = onConfirm,
        confirmEnable = selectedStartDate != null && selectedEndDate != null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueGray)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { displayedMonth = displayedMonth.minusMonths(1) }) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Previous Month",
                        tint = White
                    )
                }
                Text(
                    text = displayedMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                    color = White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                IconButton(onClick = { displayedMonth = displayedMonth.plusMonths(1) }) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Previous Month",
                        modifier = Modifier.rotate(180f),
                        tint = White
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DateSelectionField(
                    label = "Start date",
                    date = selectedStartDate,
                    isSelected = selectedField == SelectionField.START,
                    onClick = { selectedField = SelectionField.START },
                    onClear = {
                        selectedStartDate = null
                        selectedEndDate = null
                        selectedField = SelectionField.START
                        dateRangePickerState.setSelection(null, null)
                    },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.weight(.2f))

                DateSelectionField(
                    label = "End date",
                    date = selectedEndDate,
                    isSelected = selectedField == SelectionField.END,
                    onClick = { selectedField = SelectionField.END },
                    onClear = {
                        selectedEndDate = null
                        dateRangePickerState.setSelection(selectedStartDate?.let {
                            localDateToLong(it)
                        }, null)
                    },
                    modifier = Modifier.weight(1f)
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            val weekDays = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                weekDays.forEach { day ->
                    Text(
                        text = day,
                        color = LightGray,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            val daysInMonth = getMonthDays(displayedMonth)
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(daysInMonth, key = { it }) { day ->
                    CalendarDay(
                        day = day,
                        currentDisplayedMonth = displayedMonth,
                        selectedStartDay = selectedStartDate,
                        selectedEndDay = selectedEndDate,
                        onDayClicked = {
                            when (selectedField) {
                                SelectionField.START -> {
                                    if (selectedEndDate != null && day.isAfter(selectedEndDate)) {
                                        selectedStartDate = day
                                        selectedEndDate = null
                                        selectedField = SelectionField.END
                                    }
                                    selectedStartDate = day
                                    selectedField = SelectionField.END
                                }

                                SelectionField.END -> {
                                    if (selectedStartDate == null || day.isBefore(selectedStartDate)) {
                                        selectedStartDate = day
                                        selectedEndDate = null
                                        selectedField = SelectionField.END
                                    } else {
                                        selectedEndDate = day
                                        selectedField = SelectionField.START
                                    }
                                }
                            }

                            dateRangePickerState.setSelection(
                                selectedStartDate?.let { localDateToLong(it) },
                                selectedEndDate?.let { localDateToLong(it) }
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CustomCalendarPreview() {
    val dateRangePickerState = rememberDateRangePickerState()
    CalendarDialog(dateRangePickerState, {}, { Pair(null, null) })
}