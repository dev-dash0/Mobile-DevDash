package com.elfeky.devdash.ui.common.dialogs.project.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import com.elfeky.devdash.ui.common.dialogs.component.HorizontalItem
import com.elfeky.devdash.ui.common.dialogs.component.VerticalItem
import com.elfeky.devdash.ui.common.dialogs.issue.components.DateRangeInput
import com.elfeky.devdash.ui.common.dropdown_menu.MenuSelector
import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuOption
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority.Companion.priorityList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.projectStatusList
import com.elfeky.devdash.ui.theme.DevDashTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDialogContent(
    title: String,
    description: String,
    dateRangeState: DateRangePickerState,
    selectedPriority: MenuOption,
    selectedStatus: MenuOption,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (MenuOption) -> Unit,
    onStatusChange: (MenuOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VerticalItem("Project name*") {
            InputField(
                value = title,
                onValueChange = onTitleChange,
                placeholderText = "",
                modifier = Modifier.fillMaxWidth()
            )
        }

        VerticalItem("Description*") {
            InputField(
                value = description,
                onValueChange = onDescriptionChange,
                placeholderText = "",
                supportingText = { Text(text = "Write a few sentences about the project...") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        HorizontalItem(label = "Set Date") {
            DateRangeInput(state = dateRangeState, modifier = Modifier.weight(.75f))
        }

        HorizontalItem(label = "Priority") {
            MenuSelector(
                items = priorityList,
                selectedItem = selectedPriority,
                onItemSelected = { onPriorityChange(it) },
                modifier = Modifier.weight(.75f)
            )
        }

        HorizontalItem(label = "Status") {
            MenuSelector(
                items = projectStatusList,
                selectedItem = selectedStatus,
                onItemSelected = { onStatusChange(it) },
                modifier = Modifier.weight(.75f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ProjectDialogContentPreview() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val currentYear = LocalDate.now().year
    val dateRangeState = rememberDateRangePickerState(
        yearRange = currentYear..(currentYear + 5),
        selectableDates = ValidRangeSelectableDates.startingFromCurrentDay()
    )
    var selectedPriority by remember { mutableStateOf(priorityList[0]) }
    var selectedStatus by remember { mutableStateOf(projectStatusList[0]) }

    DevDashTheme {
        ProjectDialogContent(
            title,
            description,
            dateRangeState,
            selectedPriority,
            selectedStatus,
            { title = it },
            { description = it },
            { selectedPriority = it as Priority },
            { selectedStatus = it as Status }
        )
    }
}