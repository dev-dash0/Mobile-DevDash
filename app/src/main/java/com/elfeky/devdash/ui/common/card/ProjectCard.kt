package com.elfeky.devdash.ui.common.card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.card.component.CardContainer
import com.elfeky.devdash.ui.common.card.component.CardTitle
import com.elfeky.devdash.ui.common.component.StatusIndicator
import com.elfeky.devdash.ui.common.dropdown_menu.model.toProjectStatus
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
        verticalSpaceBetweenItems = 8.dp
    ) {
        CardTitle(
            issueTitle = project.name,
            date = formatDisplayDate(project.startDate) + " | " + formatDisplayDate(project.endDate)
        ) {
            StatusIndicator(project.status.toProjectStatus())
        }
        Text(
            text = project.description,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
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