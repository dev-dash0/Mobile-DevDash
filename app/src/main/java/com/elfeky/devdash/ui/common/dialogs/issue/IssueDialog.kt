package com.elfeky.devdash.ui.common.dialogs.issue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.component.CustomDivider
import com.elfeky.devdash.ui.common.component.CustomDropdownMenu
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.common.component.OutlinedInputField
import com.elfeky.devdash.ui.common.dialogs.model.DropDownMenuDataModel
import com.elfeky.devdash.ui.common.dialogs.model.assigneeList
import com.elfeky.devdash.ui.common.dialogs.model.labelList
import com.elfeky.devdash.ui.common.dialogs.model.priorityList
import com.elfeky.devdash.ui.common.dialogs.model.statusList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.LightGray
import com.elfeky.devdash.ui.theme.White

@Composable
fun IssueDialogContent(
    title: String,
    description: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    statusList: List<DropDownMenuDataModel>,
    selectedStatus: DropDownMenuDataModel,
    onStatusSelected: (DropDownMenuDataModel) -> Unit,
    priorityList: List<DropDownMenuDataModel>,
    selectedPriority: DropDownMenuDataModel,
    onPrioritySelected: (DropDownMenuDataModel) -> Unit,
    onDateClick: (Pair<Long?, Long?>) -> Unit,
    labelList: List<String>,
    onLabelSelected: (String) -> Unit,
    onAddLabelClick: () -> Unit,
    selectedLabels: SnapshotStateList<String>,
    availableAssignees: SnapshotStateList<Pair<String, String>>,
    selectedAssignees: SnapshotStateList<Pair<String, String>>
) {
//    CustomAlertDialog(onCancel = onCancel, onConfirm = onConfirm) {
        Column(
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
                    unfocusedPlaceholderColor = White,
                    focusedPlaceholderColor = White,
                    unfocusedTextColor = White,
                    focusedTextColor = White

                )
            )

            OutlinedInputField(
                value = description,
                placeholderText = "Description.......",
                onValueChanged = onDescriptionChange,
                modifier = Modifier.fillMaxWidth()
            )

            AssigneeRow(availableAssignees, selectedAssignees)
            CustomDivider()
            DatePickerRow({ date -> onDateClick(date) })
            CustomDivider()
            LabelRow(labelList, onLabelSelected, selectedLabels, onAddLabelClick)
            CustomDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                CustomDropdownMenu(
                    items = statusList,
                    selectedItem = selectedStatus,
                    onItemSelected = onStatusSelected
                )
                CustomDropdownMenu(
                    items = priorityList,
                    selectedItem = selectedPriority,
                    onItemSelected = onPrioritySelected
                )
                IconButton(
                    onClick = {/*TODO: Handle attachments}*/ }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_pin),
                        contentDescription = "Add attachments",
                        tint = LightGray
                    )
                }
            }
        }
//    }
}

@Preview
@Composable
private fun IssuePreview() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf(statusList[0]) }
    var selectedPriority by remember { mutableStateOf(priorityList[0]) }
    val selectedLabels = remember { emptyList<String>().toMutableStateList() }
    val selectedAssignees = remember { emptyList<Pair<String, String>>().toMutableStateList() }

    DevDashTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            IssueDialogContent(
                onCancel = {},
                onConfirm = {},
                title = title,
                onTitleChange = { title = it },
                description = description,
                onDescriptionChange = { description = it },
                statusList = statusList,
                selectedStatus = selectedStatus,
                onStatusSelected = { selectedStatus = it },
                priorityList = priorityList,
                selectedPriority = selectedPriority,
                onPrioritySelected = { selectedPriority = it },
                onDateClick = { Pair(null, null) },
                labelList = labelList,
                selectedLabels = selectedLabels,
                onLabelSelected = { selectedLabels.apply { if (!remove(it)) add(it) } },
                onAddLabelClick = { /*TODO: Handle add label*/ },
                availableAssignees = assigneeList.toMutableStateList(),
                selectedAssignees = selectedAssignees
            )
        }
    }
}
