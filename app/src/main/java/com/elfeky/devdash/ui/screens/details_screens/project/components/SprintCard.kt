package com.elfeky.devdash.ui.screens.details_screens.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.common.component.StartEndDateText
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.sprintList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.sprint.Sprint

@Composable
fun SprintCard(
    sprint: Sprint,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    onSprintClicked: (sprintId: Int) -> Unit
) {
    val percentage by remember(sprint.issues) {
        derivedStateOf {
            if (sprint.issues.isNotEmpty()) {
                (sprint.issues.filter { it.status == Status.Completed.text }.size.toFloat() / sprint.issues.size * 100).toInt()
            } else 0
        }
    }

    Card(
        modifier = modifier
            .clickable { onSprintClicked(sprint.id) }
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(elevation),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh.copy(
                alpha = .5f
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sprint.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "$percentage%",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${sprint.issues.size} issues",
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.secondary.copy(.3f))
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    color = MaterialTheme.colorScheme.onBackground.copy(.8f),
                    style = MaterialTheme.typography.labelSmall
                )

                StartEndDateText(sprint.startDate, sprint.endDate)
            }
        }
    }
}

@Preview
@Composable
fun PreviewSprintCard() {
    DevDashTheme {
        SprintCard(sprintList[0]) {}
    }
}