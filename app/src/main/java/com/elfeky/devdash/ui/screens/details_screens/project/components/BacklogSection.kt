package com.elfeky.devdash.ui.screens.details_screens.project.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.elfeky.devdash.ui.common.component.SwipeToDismissItem
import com.elfeky.devdash.ui.utils.itemsPaging
import com.elfeky.domain.model.issue.Issue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BacklogSection(
    backlogIssues: LazyPagingItems<Issue>, // Changed to LazyPagingItems
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
        itemsPaging(backlogIssues, key = { it.id }) { issue ->
            issue?.let {
                SwipeToDismissItem(
                    isPinned = pinnedIssues.contains(it),
                    onSwipeToPin = { onSwipeToPin(it.id) },
                    onSwipeToDelete = { onSwipeToDelete(it.id) },
                ) {
                    BacklogCard(
                        issue = it,
                        onIssueClicked = onIssueClicked
                    )
                }
            }
        }
        item {
            when (backlogIssues.loadState.append) {
                is LoadState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is LoadState.Error -> {
                    val error = backlogIssues.loadState.append as LoadState.Error
                    Text("Error loading more backlog issues: ${error.error.localizedMessage}")
                }

                else -> {}
            }
        }
        item {
            when (backlogIssues.loadState.refresh) {
                is LoadState.Loading -> {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is LoadState.Error -> {
                    val error = backlogIssues.loadState.refresh as LoadState.Error
                    Text("Error loading backlog: ${error.error.localizedMessage}")
                }

                else -> {}
            }
        }
    }
}