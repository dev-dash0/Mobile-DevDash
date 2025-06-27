package com.elfeky.devdash.ui.screens.details_screens.project.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.common.sprintList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.sprint.Sprint

@Composable
fun BacklogPage(
    sprints: List<Sprint>,
    backlogIssues: List<Issue>,
    pinnedIssues: List<Issue>,
    pinnedSprints: List<Sprint>,
    onIssueDroppedOnSprint: (Int, Int) -> Unit,
    onCreateSprintClicked: () -> Unit,
    onSwipeToDeleteSprint: (id: Int) -> Unit,
    onSwipeToPinSprint: (id: Int) -> Unit,
    onSwipeToDeleteIssue: (id: Int) -> Unit,
    onSwipeToPinIssue: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    onSprintClicked: (Int) -> Unit,
    onIssueClicked: (issue: Issue) -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CardAnimatedSize(title = "Sprint") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SprintSection(
                    sprints = sprints,
                    pinnedSprints = pinnedSprints,
                    onIssueDroppedOnSprint = onIssueDroppedOnSprint,
                    onSprintClicked = onSprintClicked,
                    onSwipeToPin = onSwipeToPinSprint,
                    onSwipeToDelete = onSwipeToDeleteSprint,
                )
                HorizontalDivider()
                TextButton(
                    onClick = onCreateSprintClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.End)
                ) {
                    Text("Add Sprint", color = MaterialTheme.colorScheme.onBackground)

                    Spacer(Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Default.AddCircleOutline,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }


        CardAnimatedSize(title = "Backlog") {
            BacklogSection(
                backlogIssues = backlogIssues,
                pinnedIssues = pinnedIssues,
                onSwipeToPin = onSwipeToPinIssue,
                onSwipeToDelete = onSwipeToDeleteIssue,
                onIssueClicked = onIssueClicked
            )
        }
    }
}

@Preview
@Composable
fun BacklogPagePreview() {
    var sprints by remember { mutableStateOf(sprintList) }
    var backlogIssues by remember { mutableStateOf(issueList) }

    DevDashTheme {
        BacklogPage(
            sprints = sprints,
            backlogIssues = backlogIssues,
            onIssueDroppedOnSprint = { _, _ -> },
            onCreateSprintClicked = {},
            onSprintClicked = {},
            pinnedIssues = emptyList(),
            pinnedSprints = emptyList(),
            onSwipeToDeleteSprint = { },
            onSwipeToPinSprint = { },
            onSwipeToDeleteIssue = { },
            onSwipeToPinIssue = { },
            onIssueClicked = { }
        )
    }
}
