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
import com.elfeky.devdash.ui.common.component.FullScreenDialog
import com.elfeky.devdash.ui.common.dialogs.assigneeList
import com.elfeky.devdash.ui.common.dialogs.issue.components.IssueDialogContent
import com.elfeky.devdash.ui.common.dialogs.labelList
import com.elfeky.devdash.ui.common.dialogs.model.IssueDataModel
import com.elfeky.devdash.ui.common.dialogs.model.User
import com.elfeky.devdash.ui.common.dialogs.model.ValidRangeSelectableDates
import com.elfeky.devdash.ui.common.dialogs.priorityList
import com.elfeky.devdash.ui.common.dialogs.statusList
import com.elfeky.devdash.ui.common.dialogs.typeList
import com.elfeky.devdash.ui.theme.DevDashTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDialog(
    onDismiss: () -> Unit,
    onSubmit: (IssueDataModel) -> Unit,
    assigneeList: List<User>,
    labelList: List<String>,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val selectedLabels = remember { mutableStateListOf<String>() }
    val assignees = remember { mutableStateListOf<User>() }
    val currentYear = LocalDate.now().year

    val dateRangeState = rememberDateRangePickerState(
        yearRange = currentYear..(currentYear + 5),
        selectableDates = ValidRangeSelectableDates.startingFromCurrentDay()
    )

    var selectedType by remember { mutableStateOf(typeList[0]) }
    var selectedPriority by remember { mutableStateOf(priorityList[0]) }
    var selectedStatus by remember { mutableStateOf(statusList[0]) }

    FullScreenDialog(
        title = "Create New Issue",
        onDismiss = onDismiss,
        onSubmit = {
            onSubmit(
                IssueDataModel(
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
            onPriorityChange = { selectedPriority = it },
            onTypeChange = { selectedType = it },
            onStatusChange = { selectedStatus = it },
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
