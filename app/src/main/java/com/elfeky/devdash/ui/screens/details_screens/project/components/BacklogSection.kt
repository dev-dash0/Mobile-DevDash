package com.elfeky.devdash.ui.screens.details_screens.project.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.SwipeToDismissItem
import com.elfeky.domain.model.issue.Issue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BacklogSection(
    backlogIssues: List<Issue>,
    pinnedIssues: List<Issue>,
    onSwipeToDelete: (id: Int) -> Unit,
    onSwipeToPin: (id: Int) -> Unit,
    onIssueClicked: (issue: Issue) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(bottom = 64.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(backlogIssues, key = { it.id }) { issue ->
            SwipeToDismissItem(
                isPinned = pinnedIssues.contains(issue),
                onSwipeToPin = { onSwipeToPin(issue.id) },
                onSwipeToDelete = { onSwipeToDelete(issue.id) },
            ) {
                BacklogCard(
                    issue = issue,
                    onIssueClicked = onIssueClicked
                )
            }
        }
    }
}