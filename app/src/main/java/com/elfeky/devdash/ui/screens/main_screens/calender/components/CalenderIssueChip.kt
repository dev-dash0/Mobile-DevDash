package com.elfeky.devdash.ui.screens.main_screens.calender.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.card.component.CardContainer
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.formatDisplayDate
import com.elfeky.domain.model.dashboard.CalenderIssue

@Composable
fun CalenderIssueChip(
    calenderIssue: CalenderIssue,
    modifier: Modifier = Modifier
) {
    CardContainer(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            VerticalDivider(
                modifier = Modifier.clip(CircleShape),
                thickness = 4.dp,
                color = calenderIssue.priority.toPriority().color
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = calenderIssue.projectName,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 32.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        fontStyle = FontStyle.Italic,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = formatDisplayDate(calenderIssue.startDate)
                                + " | "
                                + formatDisplayDate(calenderIssue.deadline),
                        color = MaterialTheme.colorScheme.outlineVariant,
                        style = MaterialTheme.typography.labelLarge

                    )
                }

                Text(
                    text = calenderIssue.title,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun CalenderIssueChipPreview() {
    DevDashTheme {
        CalenderIssueChip(
            calenderIssue = CalenderIssue(
                id = 1,
                title = "Implement User Authentication",
                projectName = "DevDash Mobile App",
                tenantName = "ElFeky",
                startDate = "2023-10-26T10:00:00",
                deadline = "2023-11-05T18:00:00",
                priority = "High",
                type = "Feature",
            )
        )
    }
}