package com.elfeky.devdash.ui.common.dialogs.calender

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.DialogContainer
import com.elfeky.devdash.ui.common.dialogs.calender.components.DateRangePickerHeader
import com.elfeky.devdash.ui.common.dialogs.model.SelectedField
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangeDialog(
    state: DateRangePickerState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val selectedStartDate = state.selectedStartDateMillis
    val selectedEndDate = state.selectedEndDateMillis

    val selectedField = when {
        selectedStartDate == null -> SelectedField.START_DATE
        selectedEndDate == null -> SelectedField.END_DATE
        else -> SelectedField.START_DATE
    }

    val displayedMonth by remember {
        mutableLongStateOf(selectedStartDate ?: selectedEndDate ?: state.displayedMonthMillis)
    }
    LaunchedEffect(displayedMonth) {
        state.displayedMonthMillis = displayedMonth
    }

    DialogContainer(
        onCancel = onDismiss,
        onConfirm = onConfirm,
        confirmEnable = selectedStartDate != null && selectedEndDate != null
    ) {
        DateRangePicker(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = (3 * LocalConfiguration.current.screenHeightDp / 4).dp),
            title = null,
            headline = {
                DateRangePickerHeader(
                    state = state,
                    selectedField = selectedField
                )
            },
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                weekdayContentColor = MaterialTheme.colorScheme.onBackground,
                subheadContentColor = MaterialTheme.colorScheme.outlineVariant,
                dayContentColor = MaterialTheme.colorScheme.onBackground,
                disabledDayContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                todayContentColor = MaterialTheme.colorScheme.tertiary,
                todayDateBorderColor = MaterialTheme.colorScheme.tertiary,
                selectedDayContainerColor = MaterialTheme.colorScheme.tertiary,
                dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.secondary,
                dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onSecondary
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DateRangeDialogPreview() {
    val dateRangePickerState = rememberDateRangePickerState()
    DevDashTheme { DateRangeDialog(dateRangePickerState, {}, { Pair(null, null) }) }
}