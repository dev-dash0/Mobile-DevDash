package com.elfeky.devdash.ui.common.dialogs.issue

import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import com.elfeky.devdash.ui.common.dialogs.component.DialogContainer
import com.elfeky.devdash.ui.common.dialogs.issue.components.IssueDialogContent
import com.elfeky.devdash.ui.common.dialogs.issue.model.IssueUiModel
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority.Companion.priorityList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.issueStatusList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type.Companion.typeList
import com.elfeky.devdash.ui.common.dropdown_menu.model.toIssueStatus
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.devdash.ui.common.dropdown_menu.model.toType
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.toEpochMillis
import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.issue.Issue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun IssueDialog(
    onDismiss: () -> Unit,
    onSubmit: (IssueUiModel) -> Unit,
    assigneeList: List<UserProfile>,
    modifier: Modifier = Modifier,
    issue: Issue? = null,
    isBacklog: Boolean = true
) {
    var title by remember { mutableStateOf(issue?.title ?: "") }
    var description by remember { mutableStateOf(issue?.description ?: "") }
    var labels = remember { mutableStateOf(issue?.labels ?: "") }
    val assignedUsers = remember {
        mutableStateListOf<UserProfile>().apply { issue?.assignedUsers?.let { addAll(it) } }
    }

    val dateRangeState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = issue?.startDate?.toEpochMillis(),
        initialSelectedEndDateMillis = issue?.deadline?.toEpochMillis()
    )

    var selectedType by remember { mutableStateOf(issue?.type?.toType() ?: typeList[0]) }
    var selectedPriority by remember {
        mutableStateOf(
            issue?.priority?.toPriority() ?: priorityList[0]
        )
    }
    var selectedStatus by remember {
        mutableStateOf(
            issue?.status?.toIssueStatus() ?: issueStatusList[0]
        )
    }

    DialogContainer(
        title = "Create New Issue",
        onDismiss = onDismiss,
        onConfirm = {
            onSubmit(
                IssueUiModel(
                    title,
                    description,
                    labels.value,
                    dateRangeState.selectedStartDateMillis,
                    dateRangeState.selectedEndDateMillis,
                    selectedType.text,
                    selectedPriority,
                    selectedStatus.text,
                    assignedUsers
                )
            )
        },
        confirmEnable = title.isNotEmpty(),
        modifier = modifier,
    ) {
        IssueDialogContent(
            title,
            description,
            assignedUsers,
            dateRangeState,
            selectedPriority,
            selectedType,
            selectedStatus,
            assigneeList,
            onTitleChange = { title = it },
            onDescriptionChange = { description = it },
            onPriorityChange = { selectedPriority = it as Priority },
            onTypeChange = { selectedType = it as Type },
            onStatusChange = { selectedStatus = it as Status },
            onLabelToggle = { labels.value = it },
            onAssigneeToggle = { if (!assignedUsers.remove(it)) assignedUsers.add(it) },
            isBacklog = isBacklog
        )
    }
}

@Preview
@Composable
private fun IssueScreenPreview() {
    DevDashTheme {
        IssueDialog({}, {}, userList)
    }
}
