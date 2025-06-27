package com.elfeky.devdash.ui.common.dialogs.sprint

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.common.dialogs.calender.model.ValidRangeSelectableDates
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentHorizontal
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentVertical
import com.elfeky.devdash.ui.common.dialogs.issue.components.DateRangeInput
import com.elfeky.devdash.ui.common.dropdown_menu.MenuSelector
import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuOption
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.sprintStatusList
import com.elfeky.devdash.ui.theme.DevDashTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SprintDialogContent(
    title: String,
    summary: String,
    description: String,
    selectedStatus: MenuOption,
    dateRangeState: DateRangePickerState,
    onTitleChange: (String) -> Unit,
    onSummaryChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onStatusChange: (MenuOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LabelledContentVertical("Title") {
            InputField(
                value = title,
                onValueChange = onTitleChange,
                placeholderText = "",
                modifier = Modifier.fillMaxWidth()
            )
        }
        LabelledContentVertical("Description") {
            InputField(
                value = description,
                onValueChange = onDescriptionChange,
                placeholderText = "",
                modifier = Modifier.fillMaxWidth()
            )
        }

        LabelledContentHorizontal(label = "Set Date") {
            DateRangeInput(state = dateRangeState, modifier = Modifier.weight(.75f))
        }

        LabelledContentHorizontal(label = "Status") {
            MenuSelector(
                items = sprintStatusList,
                selectedItem = selectedStatus,
                onItemSelected = { onStatusChange(it) },
                modifier = Modifier.weight(.75f)
            )
        }

        LabelledContentVertical("Summary") {
            InputField(
                value = summary,
                onValueChange = onSummaryChange,
                placeholderText = "",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun SprintDialogContentPreview() {
    var title by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedStatus: MenuOption by remember { mutableStateOf(sprintStatusList[0]) }

    val currentYear = LocalDate.now().year
    val dateRangeState = rememberDateRangePickerState(
        yearRange = currentYear..(currentYear + 5),
        selectableDates = ValidRangeSelectableDates.startingFromCurrentDay()
    )

    DevDashTheme {
        SprintDialogContent(
            title = title,
            summary = summary,
            description = description,
            selectedStatus = selectedStatus,
            dateRangeState = dateRangeState,
            onTitleChange = { title = it },
            onSummaryChange = { summary = it },
            onDescriptionChange = { description = it },
            onStatusChange = { selectedStatus = it }
        )
    }
}