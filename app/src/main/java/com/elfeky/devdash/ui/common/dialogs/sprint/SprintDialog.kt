package com.elfeky.devdash.ui.common.dialogs.sprint

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.dialogs.component.DialogContainer
import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuOption
import com.elfeky.devdash.ui.common.dropdown_menu.model.toSprintStatus
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.toEpochMillis
import com.elfeky.devdash.ui.utils.toStringDate
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.sprint.SprintRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SprintDialog(
    onDismiss: () -> Unit,
    onSubmit: (SprintRequest) -> Unit,
    modifier: Modifier = Modifier,
    sprint: Sprint? = null
) {
    var title by remember { mutableStateOf(sprint?.title ?: "") }
    var description by remember { mutableStateOf(sprint?.description ?: "") }
    var summary by remember { mutableStateOf(sprint?.summary ?: "") }

    val dateRangeState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = sprint?.startDate?.toEpochMillis(),
        initialSelectedEndDateMillis = sprint?.endDate?.toEpochMillis()
    )

    var selectedStatus: MenuOption by remember { mutableStateOf(sprint?.status.toSprintStatus()) }

    DialogContainer(
        title = "Create Sprint",
        onDismiss = onDismiss,
        onConfirm = {
            onSubmit(
                SprintRequest(
                    title = title,
                    description = description,
                    startDate = dateRangeState.selectedStartDateMillis!!.toStringDate(),
                    endDate = dateRangeState.selectedEndDateMillis!!.toStringDate(),
                    status = selectedStatus.text,
                    summary = summary
                )
            )
        },
        confirmEnable = title.isNotEmpty()
                && description.isNotEmpty()
                && summary.isNotEmpty()
                && dateRangeState.selectedEndDateMillis != null,
        modifier = modifier,
    ) {
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

@Preview
@Composable
private fun SprintDialogPreview() {
    DevDashTheme {
        SprintDialog({}, {})
    }
}