package com.elfeky.devdash.ui.common.card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.card.component.CardContainer
import com.elfeky.devdash.ui.common.component.IconText
import com.elfeky.devdash.ui.common.dropdown_menu.model.toIssueStatus
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.devdash.ui.common.projectList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.formatDisplayDate
import com.elfeky.domain.model.project.Project

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectCard(
    project: Project,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CardContainer(
        modifier = modifier.combinedClickable(onClick = onClick, onLongClick = onLongClick),
        contentPadding = PaddingValues(16.dp),
        verticalSpaceBetweenItems = 12.dp
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = project.name,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )


            Text(
                text = (project.startDate?.let { formatDisplayDate(it) } ?: "_")
                        + " | "
                        + (project.endDate?.let { formatDisplayDate(it) } ?: "_"),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Row(
            modifier = Modifier.align(Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val status = project.status.toIssueStatus()
            val priority = project.priority.toPriority()

            IconText(
                status,
                iconSize = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .clip(CircleShape)
                    .background(status.color.copy(alpha = .2f))
                    .padding(6.dp, 2.dp)
            )

            IconText(
                priority,
                iconSize = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .clip(CircleShape)
                    .background(status.color.copy(alpha = .2f))
                    .padding(6.dp, 2.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CompanyCardPreview() {
    DevDashTheme {
        ProjectCard(
            project = projectList[0],
            onClick = {},
            onLongClick = {}
        )
    }
}