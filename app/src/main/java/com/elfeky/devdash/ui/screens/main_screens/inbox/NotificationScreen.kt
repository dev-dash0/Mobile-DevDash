package com.elfeky.devdash.ui.screens.main_screens.inbox

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elfeky.devdash.ui.common.component.LoadingIndicator

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    Crossfade(targetState = state.value) {
        if (it.isLoading) {
            LoadingIndicator()
        } else if (!it.error.isNullOrEmpty()) {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = it.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        } else {
            NotificationContent(
                notifications = state.value.notifications,
                markRead = { viewModel.markAsRead(it) }
            )
        }
    }
}