package com.elfeky.devdash.ui.screens.extra_screens.notifications

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Crossfade(
            targetState = state.value,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (it.isLoading) {
                LoadingIndicator()
            } else if (!it.error.isNullOrEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = it.error,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                if (state.value.notifications.isNotEmpty()) {
                    NotificationContent(
                        notifications = state.value.notifications,
                        markRead = {
                            Log.d("NotificationScreen", "markRead called with id: $it")
                            viewModel.markAsRead(it)
                        }
                    )
                } else {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "You don't have new notifications",
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}