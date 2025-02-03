package com.elfeky.devdash.ui.common.dialogs.issue

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.window.Popup
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.dialogs.calender.CalendarDialog
import com.elfeky.devdash.ui.theme.Gray
import com.elfeky.devdash.ui.theme.LightGray
import com.elfeky.devdash.ui.utils.localDateToString
import com.elfeky.devdash.ui.utils.longToLocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerRow(
    onDateClick: (Pair<Long?, Long?>) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCalenderDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("Set Date") }
    val dateRangePickerState = rememberDateRangePickerState()

    Row(
        modifier = modifier
            .fillMaxWidth(.9f)
            .clickable { showCalenderDialog = !showCalenderDialog },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_calender),
            contentDescription = "time",
            modifier = Modifier.padding(8.dp),
            tint = LightGray
        )
        Text(selectedDate, color = Gray)
    }
    if (showCalenderDialog) {
        Popup(
            onDismissRequest = { showCalenderDialog = false },
            alignment = Alignment.TopStart
        ) {
            CalendarDialog(
                dateRangePickerState = dateRangePickerState,
                onCancel = { showCalenderDialog = false },
                onConfirm = {
                    val startDateMillis = dateRangePickerState.selectedStartDateMillis
                    val endDateMillis = dateRangePickerState.selectedEndDateMillis

                    val startDate = longToLocalDate(startDateMillis!!)
                    val endDate = longToLocalDate(endDateMillis!!)
                    selectedDate = when {
                        startDate == endDate -> localDateToString(startDate)

                        else -> "${localDateToString(startDate)} - ${localDateToString(endDate)}"
                    }
                    onDateClick(Pair(startDateMillis, endDateMillis))
                    showCalenderDialog = false
                }
            )
        }
    }
}

@Preview
@Composable
private fun DatePickerRowPreview() {
    DatePickerRow(onDateClick = { Pair(null, null) })
}