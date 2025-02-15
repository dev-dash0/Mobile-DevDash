package com.elfeky.devdash.ui.common.dialogs.project

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.dialogs.calender.model.ValidRangeSelectableDates
import com.elfeky.devdash.ui.common.dialogs.component.DialogContainer
import com.elfeky.devdash.ui.common.dialogs.project.components.ProjectDialogContent
import com.elfeky.devdash.ui.common.dialogs.project.model.ProjectUiModel
import com.elfeky.devdash.ui.common.dialogs.statusList
import com.elfeky.devdash.ui.common.dialogs.typeList
import com.elfeky.devdash.ui.theme.DevDashTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDialog(
    onDismiss: () -> Unit,
    onSubmit: (ProjectUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val currentYear = LocalDate.now().year
    val dateRangeState = rememberDateRangePickerState(
        yearRange = currentYear..(currentYear + 5),
        selectableDates = ValidRangeSelectableDates.startingFromCurrentDay()
    )

    var selectedType by remember { mutableStateOf(typeList[0]) }
    var selectedStatus by remember { mutableStateOf(statusList[0]) }

    DialogContainer(
        onCancel = onDismiss,
        onConfirm = {
            onSubmit(
                ProjectUiModel(
                    title,
                    description,
                    dateRangeState.selectedStartDateMillis,
                    dateRangeState.selectedEndDateMillis,
                    selectedType.text,
                    selectedStatus.text
                )
            )
        },
        title = "Create New Project",
        confirmEnable = (
                title.isNotEmpty()
                        && description.isNotEmpty()
                        && dateRangeState.selectedEndDateMillis != null),
        modifier = modifier
    ) {
        ProjectDialogContent(
            title,
            description,
            dateRangeState,
            selectedType,
            selectedStatus,
            { title = it },
            { description = it },
            { selectedType = it },
            { selectedStatus = it }
        )
    }
}

@Preview
@Composable
private fun ProjectDialogPreview() {
    DevDashTheme {
        ProjectDialog({}, {})
    }
}