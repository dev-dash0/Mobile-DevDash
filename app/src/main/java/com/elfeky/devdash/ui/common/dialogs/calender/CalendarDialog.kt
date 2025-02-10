package com.elfeky.devdash.ui.common.dialogs.calender

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.DialogContainer
import com.elfeky.devdash.ui.common.dialogs.calender.components.DateSelectionField
import com.elfeky.devdash.ui.common.dialogs.model.SelectedField
import com.elfeky.devdash.ui.theme.Black
import com.elfeky.devdash.ui.theme.DarkBlue
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.Gray
import com.elfeky.devdash.ui.theme.LightGray
import com.elfeky.devdash.ui.theme.Pink
import com.elfeky.devdash.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDialog(
    state: DateRangePickerState,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    val selectedStartDate = state.selectedStartDateMillis
    val selectedEndDate = state.selectedEndDateMillis
    val displayedMonth by remember {
        mutableLongStateOf(
            selectedStartDate ?: selectedEndDate ?: state.displayedMonthMillis
        )
    }
    val selectedField = when {
        selectedStartDate == null -> SelectedField.START_DATE
        selectedEndDate == null -> SelectedField.END_DATE
        else -> SelectedField.START_DATE
    }

    LaunchedEffect(displayedMonth) {
        state.displayedMonthMillis = displayedMonth
    }

    DialogContainer(
        onCancel = onCancel,
        onConfirm = onConfirm,
        confirmEnable = selectedStartDate != null && selectedEndDate != null
    ) {
        DialogContainer(
            onConfirm = onConfirm,
            onCancel = onCancel,
            confirmEnable = state.selectedStartDateMillis != null && state.selectedEndDateMillis != null
        ) {
            DateRangePicker(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                title = null,
                headline = {
                    Row(
                        modifier = Modifier.padding(24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DateSelectionField(
                            label = "Start Date",
                            date = state.selectedStartDateMillis,
                            isSelected = selectedField == SelectedField.START_DATE,
                            onClear = { state.setSelection(null, null) },
                            modifier = Modifier.weight(1f)
                        )
                        DateSelectionField(
                            label = "End Date",
                            date = state.selectedEndDateMillis,
                            isSelected = selectedField == SelectedField.END_DATE,
                            onClear = { state.setSelection(state.selectedStartDateMillis, null) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                },
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = DarkBlue,
                    titleContentColor = LightGray,
                    weekdayContentColor = LightGray,
                    currentYearContentColor = Pink,
                    selectedYearContentColor = LightGray,
                    dayContentColor = White,
                    disabledDayContentColor = Gray,
                    selectedDayContentColor = Black,
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CustomCalendarPreview() {
    val dateRangePickerState = rememberDateRangePickerState()
    DevDashTheme { CalendarDialog(dateRangePickerState, {}, { Pair(null, null) }) }
}