package com.elfeky.devdash.ui.common.dialogs.issue

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.dialogs.assigneeList
import com.elfeky.devdash.ui.common.dialogs.component.FullScreenDialog
import com.elfeky.devdash.ui.common.dialogs.issue.components.IssueDialogContent
import com.elfeky.devdash.ui.common.dialogs.issue.model.IssueUiModel
import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel
import com.elfeky.devdash.ui.common.dialogs.labelList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority.Companion.priorityList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.issueStatusList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type.Companion.typeList
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDialog(
    onDismiss: () -> Unit,
    onSubmit: (IssueUiModel) -> Unit,
    assigneeList: List<UserUiModel>,
    labelList: List<String>,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val selectedLabels = remember { mutableStateListOf<String>() }
    val assignees = remember { mutableStateListOf<UserUiModel>() }

    val dateRangeState = rememberDateRangePickerState()

    var selectedType by remember { mutableStateOf(typeList[0]) }
    var selectedPriority by remember { mutableStateOf(priorityList[0]) }
    var selectedStatus by remember { mutableStateOf(issueStatusList[0]) }

    FullScreenDialog(
        title = "Create New Issue",
        onDismiss = onDismiss,
        onSubmit = {
            onSubmit(
                IssueUiModel(
                    title,
                    description,
                    selectedLabels,
                    dateRangeState.selectedStartDateMillis,
                    dateRangeState.selectedEndDateMillis,
                    selectedType.text,
                    selectedPriority,
                    selectedStatus.text
                )
            )
        },
        submitEnable = (
                title.isNotEmpty()
                        && description.isNotEmpty()
                        && assignees.isNotEmpty()
                        && dateRangeState.selectedEndDateMillis != null
                ),
        modifier = modifier
    ) { paddingValues ->
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
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview
@Composable
private fun IssueScreenPreview() {
    DevDashTheme {
        IssueDialog({}, {}, assigneeList, labelList)
    }
}
