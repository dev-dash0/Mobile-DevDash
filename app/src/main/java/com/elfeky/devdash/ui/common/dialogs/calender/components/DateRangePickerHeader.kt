package com.elfeky.devdash.ui.common.dialogs.calender.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.dialogs.calender.model.SelectedField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerHeader(
    state: DateRangePickerState,
    selectedField: SelectedField,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(24.dp),
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
}