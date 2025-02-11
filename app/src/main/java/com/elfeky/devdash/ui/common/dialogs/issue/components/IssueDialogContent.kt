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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.common.component.MenuSelector
import com.elfeky.devdash.ui.common.component.OutlinedInputField
import com.elfeky.devdash.ui.common.dialogs.model.MenuDataModel
import com.elfeky.devdash.ui.common.dialogs.model.User
import com.elfeky.devdash.ui.common.dialogs.priorityList
import com.elfeky.devdash.ui.common.dialogs.statusList
import com.elfeky.devdash.ui.common.dialogs.typeList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDialogContent(
    title: String,
    description: String,
    assignees: MutableList<User>,
    dateRangeState: DateRangePickerState,
    selectedPriority: MenuDataModel,
    selectedType: MenuDataModel,
    selectedStatus: MenuDataModel,
    assigneeList: List<User>,
    labelList: List<String>,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (MenuDataModel) -> Unit,
    onTypeChange: (MenuDataModel) -> Unit,
    onStatusChange: (MenuDataModel) -> Unit,
    onLabelToggle: (String) -> Unit,
    onAssigneeToggle: (User) -> Unit,
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
                items = statusList,
                selectedItem = selectedStatus,
                onItemSelected = { onStatusChange(it) },
                showIcon = false,
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