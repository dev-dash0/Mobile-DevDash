package com.elfeky.devdash.ui.screens.details_screens.sprint.components

import android.content.ClipData
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import com.elfeky.devdash.ui.common.card.IssueCard
import com.elfeky.domain.model.issue.Issue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableIssueCard(
    issue: Issue,
    isPinned: Boolean,
    onPinClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onDrag: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .dragAndDropSource {
                detectDragGesturesAfterLongPress { _, _ ->
                    onDrag()
                    startTransfer(
                        DragAndDropTransferData(
                            ClipData.newPlainText(
                                "issue id",
                                issue.id.toString()
                            )
                        )
                    )
                }
            }
    ) {
        IssueCard(issue, isPinned, onPinClick, onDeleteClick, onEditClick)
    }
}