package com.elfeky.devdash.ui.screens.details_screens.sprint.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.issueStatusList
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.screens.details_screens.sprint.model.Board
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.issue.Issue
import kotlinx.coroutines.flow.flowOf

@Composable
fun KanbanBoard(
    issues: LazyPagingItems<Issue>,
    pinnedIssues: List<Issue>,
    onDrag: (issue: Issue) -> Unit,
    onDrop: (status: String) -> Unit,
    onPinClick: (id: Int) -> Unit,
    onDeleteClick: (id: Int) -> Unit,
    onEditClick: (issue: Issue) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(issueStatusList, key = { it.text }) { status ->
            val boardIssues = issues.itemSnapshotList.items.filter { it.status == status.text }

            BoardColumn(
                board = Board(status.text, boardIssues),
                pinnedIssues = pinnedIssues,
                onDrop = { onDrop(status.text) },
                onPinClick = onPinClick,
                onDeleteClick = onDeleteClick,
                onEditClick = onEditClick,
                onDrag = onDrag
            )
        }
    }
}

@Preview
@Composable
fun KanbanBoardPreview() {
    DevDashTheme {
        val issuesPagingItems = flowOf(PagingData.from(issueList)).collectAsLazyPagingItems()

        KanbanBoard(
            issues = issuesPagingItems,
            pinnedIssues = issueList.take(3),
            onDrag = {},
            onDrop = {},
            onPinClick = {},
            onDeleteClick = {},
            onEditClick = {}
        )
    }
}