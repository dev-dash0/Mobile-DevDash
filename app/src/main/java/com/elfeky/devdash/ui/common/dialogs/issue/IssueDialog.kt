package com.elfeky.devdash.ui.common.dialogs.issue

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.dialogs.component.DialogContainer
import com.elfeky.devdash.ui.common.dialogs.issue.components.IssueDialogContent
import com.elfeky.devdash.ui.common.dialogs.issue.model.IssueUiModel
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
    var issueState by remember {
        mutableStateOf(
            IssueUiModel(
                title = issue?.title ?: "",
                description = issue?.description ?: "",
                labels = issue?.labels ?: "",
                startDate = issue?.startDate?.toEpochMillis(),
                deadline = issue?.deadline?.toEpochMillis(),
                type = issue?.type.toType(),
                priority = issue?.priority.toPriority(),
                status = issue?.status.toIssueStatus(),
                assignedUsers = issue?.assignedUsers ?: emptyList()
            )
        )
    }

    DialogContainer(
        title = if (issue == null) "Create New Issue" else "Edit Issue",
        onDismiss = onDismiss,
        onConfirm = { onSubmit(issueState) },
        confirmEnable = issueState.title.isNotEmpty(),
        modifier = modifier,
    ) {
        IssueDialogContent(
            issue = issueState,
            assigneeList = assigneeList,
            onTitleChange = { issueState = issueState.copy(title = it) },
            onDescriptionChange = { issueState = issueState.copy(description = it) },
            onPriorityChange = { issueState = issueState.copy(priority = it) },
            onTypeChange = { issueState = issueState.copy(type = it) },
            onStatusChange = { issueState = issueState.copy(status = it) },
            onLabelToggle = { issueState = issueState.copy(labels = it) },
            onAssigneeToggle = { user ->
                issueState = issueState.copy(
                    assignedUsers =
                        if (issueState.assignedUsers.find { it.id == user.id } == null)
                            issueState.assignedUsers + user
                        else issueState.assignedUsers.filterNot { it.id == user.id }
                )
            },
            isBacklog = isBacklog
        )
    }
}

@Preview
@Composable
private fun IssueScreenPreview() {
    DevDashTheme {
        IssueDialog(
            onDismiss = {},
            onSubmit = {},
            assigneeList = userList
        )
    }
}
