package com.elfeky.devdash.ui.common.card

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.card.component.CardContainer
import com.elfeky.devdash.ui.common.card.component.CardTitle
import com.elfeky.devdash.ui.common.card.component.IssueCardFooter
import com.elfeky.devdash.ui.common.card.component.IssueCardLabels
import com.elfeky.devdash.ui.common.dialogs.assigneeList
import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel
import com.elfeky.devdash.ui.common.dialogs.labelList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun IssueCard(
    date: String,
    status: Status,
    issueTitle: String,
    labels: List<String>,
    assignees: List<UserUiModel>,
    priority: Priority,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    CardContainer(
        onClick = onClick,
        onLongClick = onLongClick,
        modifier = modifier
    ) {
        CardTitle(
            status = status,
            issueTitle = issueTitle,
            date = date
        )

        IssueCardLabels(labels = labels)

        IssueCardFooter(
            priority = priority,
            assignees = assignees,
            containerColor = containerColor
        )
    }
}

@Preview
@Composable
private fun IssueCardPreview() {
    DevDashTheme {
        IssueCard(
            date = "12 Feb | 18 Feb",
            status = Status.ToDo,
            issueTitle = "Issue Title",
            labels = labelList.take(3),
            assignees = assigneeList,
            priority = Priority.High,
            onClick = {},
            onLongClick = {}
        )
    }
}
