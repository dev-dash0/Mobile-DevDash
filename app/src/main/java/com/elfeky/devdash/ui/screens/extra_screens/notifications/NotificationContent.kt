package com.elfeky.devdash.ui.screens.extra_screens.notifications

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.notificationList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.notification.Notification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationContent(
    notifications: List<Notification>,
    markRead: (Int) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(notifications, key = { it.id }) { item ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    when (dismissValue) {
                        SwipeToDismissBoxValue.EndToStart -> {
                            markRead(item.id)
                            false
                        }

                        else -> false
                    }
                }
            )
            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            SwipeToDismissBoxValue.EndToStart -> Color.Green.copy(.3f)
                            else -> Color.Transparent
                        }, label = "dismissColor"
                    )
                    val isDismissed = dismissState.targetValue != SwipeToDismissBoxValue.Settled
                    val scale by animateFloatAsState(if (isDismissed) 1f else 0.5f)
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.DoneAll,
                            contentDescription = "Mark as read",
                            modifier = Modifier.scale(scale),
                            tint = Color.White
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enableDismissFromStartToEnd = false,
                enableDismissFromEndToStart = true,
            ) {
                NotificationCard(item = item, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Preview
@Composable
fun PreviewFinalInboxScreen() {
    DevDashTheme {
        NotificationContent(notificationList) {}
    }
}