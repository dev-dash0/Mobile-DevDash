package com.elfeky.devdash.ui.common.dialogs.issue.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
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
import com.elfeky.devdash.ui.common.component.LabelInput
import com.elfeky.devdash.ui.common.component.OutlinedInputField
import com.elfeky.devdash.ui.common.dialogs.calender.model.ValidRangeSelectableDates
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentHorizontal
import com.elfeky.devdash.ui.common.dropdown_menu.MenuSelector
import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuOption
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority.Companion.priorityList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.issueStatusList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type.Companion.typeList
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.account.UserProfile
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDialogContent(
    title: String,
    description: String,
    assignees: MutableList<UserProfile>,
    dateRangeState: DateRangePickerState,
    selectedPriority: MenuOption,
    selectedType: MenuOption,
    selectedStatus: MenuOption,
    assigneeList: List<UserProfile>,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (MenuOption) -> Unit,
    onTypeChange: (MenuOption) -> Unit,
    onStatusChange: (MenuOption) -> Unit,
    onLabelToggle: (String) -> Unit,
    onAssigneeToggle: (UserProfile) -> Unit,
    modifier: Modifier = Modifier,
    isBacklog: Boolean = true
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        InputField(
            value = title,
            placeholderText = "Untitled Issue...",
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
//                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(.5f),
//                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.secondary
            )
        )

        OutlinedInputField(
            value = description,
            placeholderText = "Description.......",
            onValueChanged = { onDescriptionChange(it) },
            modifier = Modifier
                .imePadding()
                .fillMaxWidth()
                .height(120.dp)
        )

        if (!isBacklog) {
            LabelInput(
                onAddLabel = { onLabelToggle(it) },
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider(thickness = 2.dp)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MenuSelector(
                items = priorityList,
                selectedItem = selectedPriority,
                onItemSelected = { onPriorityChange(it) }
            )

            MenuSelector(
                items = typeList,
                selectedItem = selectedType,
                onItemSelected = { onTypeChange(it) }
            )
        }

        if (!isBacklog) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MenuSelector(
                    items = issueStatusList,
                    selectedItem = selectedStatus,
                    onItemSelected = { onStatusChange(it) }
                )

                UserPicker(
                    assigneeList,
                    assignees,
                    onUserSelected = { onAssigneeToggle(it) }
                )
            }

            HorizontalDivider(thickness = 2.dp)

            LabelledContentHorizontal(label = "Set Date") {
                DateRangeInput(state = dateRangeState, modifier = Modifier.weight(.75f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun IssueDialogContentPreview() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedLabels = remember { mutableStateOf("") }
    val assignees = remember { mutableStateListOf<UserProfile>() }

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
            userList,
            onTitleChange = { title = it },
            onDescriptionChange = { description = it },
            onPriorityChange = { selectedPriority = it as Priority },
            onTypeChange = { selectedType = it as Type },
            onStatusChange = { selectedStatus = it as Status },
            onLabelToggle = { selectedLabels.value = it },
            onAssigneeToggle = { if (!assignees.remove(it)) assignees.add(it) },
        )
    }
}