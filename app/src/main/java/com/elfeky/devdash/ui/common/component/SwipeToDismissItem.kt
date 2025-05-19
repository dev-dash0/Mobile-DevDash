package com.elfeky.devdash.ui.common.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.card.ProjectCard
import com.elfeky.devdash.ui.common.dropdown_menu.model.toProjectStatus
import com.elfeky.devdash.ui.screens.details_screens.components.projectList
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun SwipeToDismissItem(
    isPinned: Boolean,
    onSwipeToDelete: () -> Unit,
    onSwipeToPin: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit),
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onSwipeToPin()
                    false
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onSwipeToDelete()
                    true
                }

                SwipeToDismissBoxValue.Settled -> false
            }
        },
        positionalThreshold = { totalDistance -> totalDistance * .5f }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val direction = dismissState.dismissDirection
            val isDismissed = dismissState.targetValue != SwipeToDismissBoxValue.Settled
            val scale by animateFloatAsState(if (isDismissed) 1f else 0.5f)
            val color by animateColorAsState(
                when (direction) {
                    SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.surface
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                }
            )
            val icon by remember(isPinned) {
                derivedStateOf {
                    when (dismissState.dismissDirection) {
                        SwipeToDismissBoxValue.StartToEnd -> {
                            if (isPinned) R.drawable.ic_un_pin else R.drawable.ic_pin
                        }

                        SwipeToDismissBoxValue.EndToStart -> R.drawable.ic_trash
                        else -> null
                    }
                }
            }
            val align by remember {
                derivedStateOf {
                    when (dismissState.dismissDirection) {
                        SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                        SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                        else -> Alignment.Center
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color, CardDefaults.shape)
                    .padding(16.dp),
                contentAlignment = align
            ) {
                if (icon != null) {
                    Icon(
                        painter = painterResource(icon!!),
                        contentDescription = "",
                        modifier = Modifier
                            .scale(scale)
                            .size(48.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        modifier = modifier,
        content = content
    )
}

@Preview
@Composable
private fun SwipeToDismissItemPreview() {
    val project = projectList[0]

    DevDashTheme {
        SwipeToDismissItem(false, {}, {}) {
            ProjectCard(
                date = project.endDate,
                status = project.status.toProjectStatus(),
                issueTitle = project.name,
                description = project.description,
                onClick = { },
                onLongClick = { }
            )
        }
    }
}
