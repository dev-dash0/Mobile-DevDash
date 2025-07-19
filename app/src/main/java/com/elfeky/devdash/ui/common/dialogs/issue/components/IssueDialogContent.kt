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
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentHorizontal
import com.elfeky.devdash.ui.common.dialogs.issue.model.IssueUiModel
import com.elfeky.devdash.ui.common.dropdown_menu.MenuSelector
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority.Companion.priorityList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.issueStatusList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type.Companion.typeList
import com.elfeky.devdash.ui.common.dropdown_menu.model.toIssueStatus
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.devdash.ui.common.dropdown_menu.model.toType
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.toEpochMillis
import com.elfeky.domain.model.account.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDialogContent(
    issue: IssueUiModel,
    dateRangeState: DateRangePickerState,
    assigneeList: List<UserProfile>,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (Priority) -> Unit,
    onTypeChange: (Type) -> Unit,
    onStatusChange: (Status) -> Unit,
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
            value = issue.title,
            placeholderText = "Untitled Issue...",
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.secondary
            )
        )

        OutlinedInputField(
            value = issue.description ?: "",
            placeholderText = "Description.......",
            onValueChanged = { onDescriptionChange(it) },
            modifier = Modifier
                .imePadding()
                .fillMaxWidth()
                .height(120.dp)
        )

        if (!isBacklog) {
            LabelInput(
                labels = issue.labels,
                modifier = Modifier.fillMaxWidth(),
                onAddLabel = { onLabelToggle(it) }
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
                selectedItem = issue.priority,
                onItemSelected = { onPriorityChange(it as Priority) }
            )

            MenuSelector(
                items = typeList,
                selectedItem = issue.type,
                onItemSelected = { onTypeChange(it as Type) }
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
                    selectedItem = issue.status,
                    onItemSelected = { onStatusChange(it as Status) }
                )

                UserPicker(
                    availableUsers = assigneeList,
                    selectedUsers = issue.assignedUsers,
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
    var issue by remember {
        mutableStateOf(
            IssueUiModel(
                title = issueList[0].title,
                description = issueList[0].description ?: "",
                labels = issueList[0].labels ?: "",
                startDate = issueList[0].startDate?.toEpochMillis(),
                deadline = issueList[0].deadline?.toEpochMillis(),
                type = issueList[0].type.toType(),
                priority = issueList[0].priority.toPriority(),
                status = issueList[0].status.toIssueStatus(),
                assignedUsers = issueList[0].assignedUsers
            )
        )
    }

    DevDashTheme {
        IssueDialogContent(
            issue = issue,
            dateRangeState = rememberDateRangePickerState(),
            assigneeList = userList,
            onTitleChange = { issue = issue.copy(title = it) },
            onDescriptionChange = { issue = issue.copy(description = it) },
            onPriorityChange = { issue = issue.copy(priority = it) },
            onTypeChange = { issue = issue.copy(type = it) },
            onStatusChange = { issue = issue.copy(status = it) },
            onLabelToggle = { issue = issue.copy(labels = it) },
            onAssigneeToggle = { user ->
                val currentAssignees = issue.assignedUsers
                val updatedAssignees = if (currentAssignees.any { it.id == user.id }) {
                    currentAssignees.filterNot { it.id == user.id }
                } else currentAssignees + user
                issue = issue.copy(assignedUsers = updatedAssignees)
            }
        )
    }
}