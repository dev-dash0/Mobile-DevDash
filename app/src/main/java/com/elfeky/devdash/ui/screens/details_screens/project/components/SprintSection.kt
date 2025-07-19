package com.elfeky.devdash.ui.screens.details_screens.project.components

import android.content.ClipDescription
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.elfeky.devdash.ui.common.card.SprintCard
import com.elfeky.devdash.ui.common.component.SwipeToDismissItem
import com.elfeky.devdash.ui.utils.itemsPaging
import com.elfeky.domain.model.sprint.Sprint

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SprintSection(
    sprints: LazyPagingItems<Sprint>,
    pinnedSprints: List<Sprint>,
    onIssueDroppedOnSprint: (Int, Int) -> Unit,
    onSwipeToDelete: (id: Int) -> Unit,
    onSwipeToPin: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    onSprintClicked: (Int) -> Unit
) {
    var hoveredSprintId by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsPaging(sprints, key = { it.id.toString() + it.title }) { sprint ->
            sprint.let {
                val isHovered = hoveredSprintId == it.id
                val animatedElevation by animateDpAsState(
                    targetValue = if (isHovered) 12.dp else 0.dp,
                    label = "sprintElevation"
                )
                val animatedPadding by animateDpAsState(
                    targetValue = if (isHovered) 0.dp else 4.dp,
                    label = "sprintPadding"
                )

                SwipeToDismissItem(
                    isPinned = pinnedSprints.contains(it),
                    onSwipeToPin = { onSwipeToPin(it.id) },
                    onSwipeToDelete = { onSwipeToDelete(it.id) },
                    modifier = Modifier
                        .padding(animatedPadding)
                        .dragAndDropTarget(
                            shouldStartDragAndDrop = { event ->
                                event.toAndroidDragEvent().clipDescription?.hasMimeType(
                                    ClipDescription.MIMETYPE_TEXT_PLAIN
                                ) == true
                            },
                            target = remember(it.id) {
                                object : DragAndDropTarget {
                                    override fun onDrop(event: DragAndDropEvent): Boolean {
                                        val issueId = event.toAndroidDragEvent()
                                            .clipData.getItemAt(0).text.toString()
                                            .toIntOrNull()
                                        if (issueId != null) {
                                            onIssueDroppedOnSprint(issueId, it.id)
                                            hoveredSprintId = null
                                            return true
                                        }
                                        return false
                                    }

                                    override fun onEntered(event: DragAndDropEvent) {
                                        hoveredSprintId = it.id
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
                        sprint = it,
                        elevation = animatedElevation,
                        onSprintClicked = onSprintClicked
                    )
                }
            }
        }
        item {
            when (sprints.loadState.append) {
                is LoadState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is LoadState.Error -> {
                    val error = sprints.loadState.append as LoadState.Error
                    Text("Error loading more sprints: ${error.error.localizedMessage}")
                }

                else -> {}
            }
        }
        item {
            when (sprints.loadState.refresh) {
                is LoadState.Loading -> {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is LoadState.Error -> {
                    val error = sprints.loadState.refresh as LoadState.Error
                    Text("Error loading sprints: ${error.error.localizedMessage}")
                }

                else -> {}
            }
        }
    }
}