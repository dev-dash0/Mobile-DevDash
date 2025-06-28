package com.elfeky.devdash.ui.screens.details_screens.project.components

import android.content.ClipDescription
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.SwipeToDismissItem
import com.elfeky.domain.model.sprint.Sprint

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SprintSection(
    sprints: List<Sprint>,
    pinnedSprints: List<Sprint>,
    onIssueDroppedOnSprint: (Int, Int) -> Unit,
    onSwipeToDelete: (id: Int) -> Unit,
    onSwipeToPin: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    onSprintClicked: (Int) -> Unit
) {
    var hoveredSprintId by remember { mutableStateOf<Int?>(null) }

    val itemSpacing = 12.dp
    val averageSprintCardHeight = 50.dp

    val desiredHeight =
        if (sprints.size > 4) (averageSprintCardHeight * 4) + (itemSpacing * 3) else (averageSprintCardHeight * (sprints.size + 1)) + (itemSpacing * (sprints.size - 1))

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .height(desiredHeight),
        verticalArrangement = Arrangement.spacedBy(itemSpacing)
    ) {
        items(sprints, key = { it.id.toString() + it.title }) { sprint ->
            val isHovered = hoveredSprintId == sprint.id
            val animatedElevation by animateDpAsState(
                targetValue = if (isHovered) 12.dp else 0.dp,
                label = "sprintElevation"
            )
            val animatedPadding by animateDpAsState(
                targetValue = if (isHovered) 0.dp else 4.dp,
                label = "sprintPadding"
            )

            SwipeToDismissItem(
                isPinned = pinnedSprints.contains(sprint),
                onSwipeToPin = { onSwipeToPin(sprint.id) },
                onSwipeToDelete = { onSwipeToDelete(sprint.id) },
                modifier = Modifier
                    .padding(animatedPadding)
                    .dragAndDropTarget(
                        shouldStartDragAndDrop = { event ->
                            event.toAndroidDragEvent().clipDescription?.hasMimeType(
                                ClipDescription.MIMETYPE_TEXT_PLAIN
                            ) == true
                        },
                        target = remember(sprint.id) {
                            object : DragAndDropTarget {
                                override fun onDrop(event: DragAndDropEvent): Boolean {
                                    val issueId = event.toAndroidDragEvent()
                                        .clipData.getItemAt(0).text.toString()
                                        .toIntOrNull()
                                    if (issueId != null) {
                                        onIssueDroppedOnSprint(issueId, sprint.id)
                                        hoveredSprintId = null
                                        return true
                                    }
                                    return false
                                }

                                override fun onEntered(event: DragAndDropEvent) {
                                    hoveredSprintId = sprint.id
                                }

                                override fun onEnded(event: DragAndDropEvent) {
                                    hoveredSprintId = null
                                }

                                override fun onExited(event: DragAndDropEvent) {
                                    hoveredSprintId = null
                                }
                            }
                        }
                    )
            ) {
                SprintCard(
                    sprint = sprint,
                    elevation = animatedElevation,
                    onSprintClicked = onSprintClicked
                )
            }
        }
    }
}