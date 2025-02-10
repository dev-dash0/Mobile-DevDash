package com.elfeky.devdash.ui.common.dialogs.calender.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.DialogContainer
import com.elfeky.devdash.ui.common.dialogs.model.SelectedField
import com.elfeky.devdash.ui.theme.DarkBlue
import com.elfeky.devdash.ui.theme.Gray
import com.elfeky.devdash.ui.theme.LightGray
import com.elfeky.devdash.ui.theme.Pink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
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
                containerColor = DarkBlue,
                titleContentColor = LightGray,
                headlineContentColor = LightGray,
                weekdayContentColor = LightGray,
                subheadContentColor = LightGray,
                yearContentColor = LightGray,
                currentYearContentColor = Pink,
                selectedYearContentColor = LightGray,
                dayContentColor = LightGray,
                disabledDayContentColor = Gray,
                selectedDayContentColor = LightGray,
            )
        )
    }
}