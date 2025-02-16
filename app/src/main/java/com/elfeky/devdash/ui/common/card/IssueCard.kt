package com.elfeky.devdash.ui.common.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.Status
import com.elfeky.devdash.ui.common.card.component.IssueCardFooter
import com.elfeky.devdash.ui.common.card.component.IssueCardHeader
import com.elfeky.devdash.ui.common.card.component.IssueCardLabels
import com.elfeky.devdash.ui.common.card.component.IssueCardTitle
import com.elfeky.devdash.ui.common.dialogs.assigneeList
import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel
import com.elfeky.devdash.ui.common.dialogs.labelList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.Red

@Composable
fun IssueCard(
    projectName: String,
    date: String,
    status: Status,
    issueTitle: String,
    labels: List<String>,
    assignees: List<UserUiModel>,
    priorityTint: Color,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    elevation: CardElevation = CardDefaults.cardElevation(0.dp),
    headTextStyle: TextStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
    titleTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = elevation
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IssueCardHeader(projectName, date, headTextStyle)
            IssueCardTitle(status, issueTitle, titleTextStyle)
            IssueCardLabels(labels)
            IssueCardFooter(priorityTint, assignees, containerColor)
        }
    }
}

@Preview
@Composable
private fun IssueCardPreview() {
    DevDashTheme {
        IssueCard(
            "DevDash",
            "12 Feb | 18 Feb",
            Status.Canceled,
            "Issue Title",
            labelList.take(3),
            assigneeList,
            Red
        )
    }
}
