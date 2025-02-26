package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.DevDashTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTextField(modifier: Modifier = Modifier, onClick: (String) -> Unit) {

    var date by remember {
        mutableStateOf("")
    }
    var isDatePickerShown by remember {
        mutableStateOf(false)
    }


    OutlinedTextField(
        value = date,
        enabled = false,
        textStyle = TextStyle(color = Color.Black),
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = Color.Gray,
            disabledContainerColor = Color.White
        ),
        onValueChange = { date = it },
        shape = RoundedCornerShape(7.dp),
        placeholder = {
            Text(
                text = "Date Of Brith",
                style = MaterialTheme.typography.titleMedium,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable { isDatePickerShown = true },
        readOnly = true,

        trailingIcon = {
            IconButton(onClick = { isDatePickerShown = true }) {

                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "select a date ",
                    modifier = modifier.size(24.dp)
                )
            }

        }
    )

    if (isDatePickerShown) {
        DatePickerChooser(
            onConfirm = {
                var c = Calendar.getInstance()
                c.timeInMillis =
                    it.selectedDateMillis!! // calender class is used to convert from ms to date
                var dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                date = dateFormatter.format(c.time)
                onClick(date)
                isDatePickerShown = false
            },
            onDismiss = {
                isDatePickerShown = false
            },
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerChooser(onConfirm: (DatePickerState) -> Unit, onDismiss: () -> Unit) {

    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    DatePickerDialog(
        onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = { onConfirm(datePickerState) }) {
                Text(text = "Ok", color = MaterialTheme.colorScheme.onBackground)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel", color = MaterialTheme.colorScheme.onBackground)
            }
        }

    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                weekdayContentColor = MaterialTheme.colorScheme.onBackground,
                subheadContentColor = MaterialTheme.colorScheme.outlineVariant,
                dayContentColor = MaterialTheme.colorScheme.onBackground,
                disabledDayContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                todayContentColor = MaterialTheme.colorScheme.onBackground,
                selectedDayContainerColor = MaterialTheme.colorScheme.tertiary,
            )
        )
    }

}

@Preview
@Composable
private fun PreviewDateTextField() {
    DevDashTheme {
        DateTextField { }
    }

}