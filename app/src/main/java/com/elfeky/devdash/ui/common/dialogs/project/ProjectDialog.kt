package com.elfeky.devdash.ui.common.dialogs.project

import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.dialogs.component.DialogContainer
import com.elfeky.devdash.ui.common.dialogs.priorityList
import com.elfeky.devdash.ui.common.dialogs.project.components.ProjectDialogContent
import com.elfeky.devdash.ui.common.dialogs.projectStatusList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.nowLocalDate
import com.elfeky.devdash.ui.utils.toEpochMillis
import com.elfeky.devdash.ui.utils.toStringDate
import com.elfeky.domain.model.project.ProjectRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDialog(
    onDismiss: () -> Unit,
    onSubmit: (ProjectRequest) -> Unit,
    modifier: Modifier = Modifier,
    project: ProjectRequest? = null,
    dateRangeState: DateRangePickerState = rememberDateRangePickerState()
) {
    var title by remember { mutableStateOf(project?.name ?: "") }
    var description by remember { mutableStateOf(project?.description ?: "") }

    LaunchedEffect(project) {
        project?.let {
            dateRangeState.setSelection(it.startDate.toEpochMillis(), it.endDate.toEpochMillis())
        }
    }

    var selectedPriority by remember {
        mutableStateOf(priorityList.find { it.text == project?.priority } ?: priorityList[0])
    }
    var selectedStatus by remember {
        mutableStateOf(projectStatusList.find { it.text == project?.status }
            ?: projectStatusList[0])
    }

    DialogContainer(
        onCancel = onDismiss,
        onConfirm = {
            onSubmit(
                ProjectRequest(
                    name = title,
                    description = description,
                    creationDate = nowLocalDate(),
                    startDate = dateRangeState.selectedStartDateMillis!!.toStringDate(),
                    endDate = dateRangeState.selectedEndDateMillis!!.toStringDate(),
                    priority = selectedPriority.text,
                    status = selectedStatus.text
                )
            )
        },
        title = if (project == null) "Create New Project" else "Edit Project",
        confirmEnable = (
                title.isNotEmpty() &&
                        description.isNotEmpty() &&
                        dateRangeState.selectedEndDateMillis != null
                ),
        modifier = modifier
    ) {
        ProjectDialogContent(
            title = title,
            description = description,
            dateRangeState = dateRangeState,
            selectedPriority = selectedPriority,
            selectedStatus = selectedStatus,
            onTitleChange = { title = it },
            onDescriptionChange = { description = it },
            onPriorityChange = { selectedPriority = it },
            onStatusChange = { selectedStatus = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ProjectDialogPreview() {
    DevDashTheme {
        ProjectDialog({}, {})
    }
}