package com.elfeky.devdash.ui.screens.details_screens.sprint.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.screens.details_screens.sprint.model.Board
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.boardColumnGradient
import com.elfeky.domain.model.issue.Issue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoardColumn(
    board: Board,
    pinnedIssues: List<Issue>,
    onDrop: (event: DragAndDropEvent) -> Unit,
    onPinClick: (id: Int) -> Unit,
    onDeleteClick: (id: Int) -> Unit,
    onEditClick: (issue: Issue) -> Unit,
    onDrag: (issue: Issue) -> Unit,
    modifier: Modifier = Modifier
) {
    var isTargeted by remember { mutableStateOf(false) }

    val dropTargetModifier = Modifier.dragAndDropTarget(
        shouldStartDragAndDrop = { true },
        target = remember {
            object : DragAndDropTarget {
                override fun onEnded(event: DragAndDropEvent) {
                    isTargeted = false
                }

                override fun onEntered(event: DragAndDropEvent) {
                    isTargeted = true
                }

                override fun onDrop(event: DragAndDropEvent): Boolean {
                    onDrop(event)
                    return true
                }

                override fun onExited(event: DragAndDropEvent) {
                    isTargeted = false
                }
            }
        }
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(250.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(boardColumnGradient)
            .then(dropTargetModifier)
            .padding(8.dp)
    ) {
        BoardHeader(board.title, board.issues.size)

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(board.issues, key = { it.id }) { issue ->
                DraggableIssueCard(
                    issue = issue,
                    isPinned = pinnedIssues.contains(issue),
                    onPinClick = { onPinClick(issue.id) },
                    onDeleteClick = { onDeleteClick(issue.id) },
                    onEditClick = { onEditClick(issue) },
                    onDrag = { onDrag(issue) }
                )
            }
        }
        if (isTargeted) {
            DashedDropZone()
        }
    }
}

@Preview
@Composable
private fun BoardColumnPreview() {
    DevDashTheme {
        BoardColumn(
            board = Board(
                title = "To Do",
                issues = issueList.take(4)
            ),
            pinnedIssues = issueList.take(2),
            onDrop = { },
            onPinClick = {},
            onDeleteClick = {},
            onEditClick = {},
            onDrag = {}
        )
    }
}
