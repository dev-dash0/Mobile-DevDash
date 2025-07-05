package com.elfeky.devdash.ui.screens.extra_screens.notifications

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
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

    LaunchedEffect(lazyListState, notifications) {
        Log.d("NotificationScreen", "LaunchedEffect called")
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo
                .map { notifications[it.index] }
                .filter { !it.isRead }
                .map { it.id }
                .toSet()
        }.collect { visibleUnreadIds ->
            Log.d("NotificationScreen", "visibleUnreadIds: $visibleUnreadIds")
            visibleUnreadIds.forEach { id ->
                markRead(id)
            }
        }
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(notifications) { item ->
            NotificationCard(item = item, modifier = Modifier.fillMaxWidth())
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