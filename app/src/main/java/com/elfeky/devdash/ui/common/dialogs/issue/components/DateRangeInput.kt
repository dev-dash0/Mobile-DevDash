package com.elfeky.devdash.ui.common.dialogs.issue.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.component.formatDateRange
import com.elfeky.devdash.ui.common.dialogs.calender.DateRangeDialog
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangeInput(
    state: DateRangePickerState,
    modifier: Modifier = Modifier
) {
    var showCalendarDialog by remember { mutableStateOf(false) }
    var selectedDateDisplay by remember { mutableStateOf("_") }

    Row(modifier = modifier.fillMaxWidth()) {
        TextButton(onClick = { showCalendarDialog = true }) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_calender),
                    contentDescription = "Calendar",
                    modifier = Modifier.padding(8.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(text = selectedDateDisplay, color = MaterialTheme.colorScheme.onBackground)
        }

    }

    if (showCalendarDialog) {
        DateRangeDialog(
            state = state,
            onDismiss = { showCalendarDialog = false },
            onConfirm = {
                selectedDateDisplay = formatDateRange(
                    state.selectedStartDateMillis,
                    state.selectedEndDateMillis
                )
                showCalendarDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DatePickerRowPreview() {
    val state = rememberDateRangePickerState()
    DevDashTheme { DateRangeInput(state = state) }
}