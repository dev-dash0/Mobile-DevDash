package com.elfeky.devdash.ui.common.dialogs.issue.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.common.component.OutlinedInputField
import com.elfeky.devdash.ui.common.dialogs.assigneeList
import com.elfeky.devdash.ui.common.dialogs.calender.model.ValidRangeSelectableDates
import com.elfeky.devdash.ui.common.dialogs.component.HorizontalItem
import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel
import com.elfeky.devdash.ui.common.dialogs.labelList
import com.elfeky.devdash.ui.common.dropdown_menu.MenuSelector
import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuOption
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority.Companion.priorityList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.issueStatusList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type.Companion.typeList
import com.elfeky.devdash.ui.theme.DevDashTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDialogContent(
    title: String,
    description: String,
    assignees: MutableList<UserUiModel>,
    dateRangeState: DateRangePickerState,
    selectedPriority: MenuOption,
    selectedType: MenuOption,
    selectedStatus: MenuOption,
    assigneeList: List<UserUiModel>,
    labelList: List<String>,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (MenuOption) -> Unit,
    onTypeChange: (MenuOption) -> Unit,
    onStatusChange: (MenuOption) -> Unit,
    onLabelToggle: (String) -> Unit,
    onAssigneeToggle: (UserUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        InputField(
            value = title,
            placeholderText = "Untitled Issue",
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground

            )
        )

        HorizontalItem(label = "Assignees") {
            AssigneePicker(
                assigneeList,
                assignees,
                onAssigneeSelected = { onAssigneeToggle(it) },
                modifier = Modifier.weight(.75f)
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

        HorizontalItem(label = "Type") {
            MenuSelector(
                items = typeList,
                selectedItem = selectedType,
                onItemSelected = { onTypeChange(it) },
                modifier = Modifier.weight(.75f)
            )
        }

        HorizontalItem(label = "Status") {
            MenuSelector(
                items = issueStatusList,
                selectedItem = selectedStatus,
                onItemSelected = { onStatusChange(it) },
                modifier = Modifier.weight(.75f)
            )
        }

        HorizontalDivider(thickness = 2.dp)

        LabelRow(
            labelList = labelList,
            onLabelSelected = { onLabelToggle(it) },
            onAddLabelClick = { /* TODO: Handle add label */ },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        HorizontalDivider(thickness = 2.dp)

        OutlinedInputField(
            value = description,
            placeholderText = "Description.......",
            onValueChanged = { onDescriptionChange(it) },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .imePadding()
                .fillMaxWidth()
                .height(150.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun IssueDialogContentPreview() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val selectedLabels = remember { mutableStateListOf<String>() }
    val assignees = remember { mutableStateListOf<UserUiModel>() }

    val currentYear = LocalDate.now().year
    val dateRangeState = rememberDateRangePickerState(
        yearRange = currentYear..(currentYear + 5),
        selectableDates = ValidRangeSelectableDates.startingFromCurrentDay()
    )

    var selectedType by remember { mutableStateOf(typeList[0]) }
    var selectedPriority by remember { mutableStateOf(priorityList[0]) }
    var selectedStatus by remember { mutableStateOf(issueStatusList[0]) }

    DevDashTheme {
        IssueDialogContent(
            title,
            description,
            assignees,
            dateRangeState,
            selectedPriority,
            selectedType,
            selectedStatus,
            assigneeList,
            labelList,
            onTitleChange = { title = it },
            onDescriptionChange = { description = it },
            onPriorityChange = { selectedPriority = it as Priority },
            onTypeChange = { selectedType = it as Type },
            onStatusChange = { selectedStatus = it as Status },
            onLabelToggle = { if (!selectedLabels.remove(it)) selectedLabels.add(it) },
            onAssigneeToggle = { if (!assignees.remove(it)) assignees.add(it) },
        )
    }
}