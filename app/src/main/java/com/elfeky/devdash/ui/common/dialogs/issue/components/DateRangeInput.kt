package com.elfeky.devdash.ui.common.dialogs.issue.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import com.elfeky.devdash.ui.common.dialogs.calender.CalenderDialog
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.formatDateRange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangeInput(
    state: DateRangePickerState,
    modifier: Modifier = Modifier
) {
    var showCalendarDialog by remember { mutableStateOf(false) }
    val selectedDateDisplay by remember {
        derivedStateOf {
            if (state.selectedEndDateMillis != null) {
                formatDateRange(state.selectedStartDateMillis, state.selectedEndDateMillis)
            } else "_"
        }
    }

    Box(modifier = modifier) {
        TextButton(onClick = { showCalendarDialog = true }) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_calender),
                    contentDescription = "Calendar",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(text = selectedDateDisplay, color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }

    if (showCalendarDialog) {
        CalenderDialog(
            state = state,
            onDismiss = { showCalendarDialog = false },
            onConfirm = { showCalendarDialog = false }
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