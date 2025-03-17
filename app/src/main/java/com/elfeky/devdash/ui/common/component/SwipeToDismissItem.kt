package com.elfeky.devdash.ui.common.component

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.card.ProjectCard
import com.elfeky.devdash.ui.common.toStatus
import com.elfeky.devdash.ui.screens.details_screens.project.model.projectList
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun SwipeToDismissItem(
    dismissState: SwipeToDismissBoxState,
    isPinned: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit),
) {
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.dismissDirection) {
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
                        else -> Alignment.CenterEnd
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
                        modifier = Modifier.size(48.dp),
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
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            when (state) {
                SwipeToDismissBoxValue.StartToEnd -> true
                SwipeToDismissBoxValue.EndToStart -> true
                SwipeToDismissBoxValue.Settled -> true
            }
        }
    )

    DevDashTheme {
        SwipeToDismissItem(dismissState, false) {
            ProjectCard(
                date = project.endDate,
                status = project.status.toStatus(),
                issueTitle = project.name,
                description = project.description,
                onClick = { },
                onLongClick = { }
            )
        }
    }
}
