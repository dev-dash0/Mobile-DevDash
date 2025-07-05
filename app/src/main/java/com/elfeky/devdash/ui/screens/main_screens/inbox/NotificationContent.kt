package com.elfeky.devdash.ui.screens.main_screens.inbox

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.notificationList
import com.elfeky.devdash.ui.screens.details_screens.company.components.FilterChipRow
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.notification.Notification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationContent(
    notifications: List<Notification>,
    markRead: (Int) -> Unit
) {
    val lazyListState = rememberLazyListState()

    val filterChips = listOf("All", "Mentions", "Assigned to me", "Unread")
    var selectedFilterChip by remember { mutableStateOf(filterChips[0]) }

    val messages by remember(notifications, selectedFilterChip) {
        mutableStateOf(notifications.sortedByDescending { it.createdAt }.filter {
            when (selectedFilterChip) {
                "All" -> true
                "Mentions" -> it.message.contains("mentioned", ignoreCase = true)
                "Assigned to me" -> it.message.contains("assigned", ignoreCase = true)
                "Unread" -> !it.isRead
                else -> true
            }
        })
    }


    LaunchedEffect(lazyListState, messages) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo
                .map { messages[it.index] }
                .filter { !it.isRead }
                .map { it.id }
                .toSet()
        }.collect { visibleUnreadIds ->
            visibleUnreadIds.forEach { id ->
                markRead(id)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FilterChipRow(
            choices = filterChips,
            onChoiceSelected = { index -> selectedFilterChip = filterChips[index] }
        )

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(messages) { item ->
                NotificationCard(item = item, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Preview
@Composable
fun PreviewFinalInboxScreen() {
    DevDashTheme {
        NotificationContent(notificationList, {})
    }
}