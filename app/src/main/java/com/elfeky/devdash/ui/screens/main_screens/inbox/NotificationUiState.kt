package com.elfeky.devdash.ui.screens.main_screens.inbox

import com.elfeky.domain.model.notification.Notification

data class NotificationUiState(
    val notifications: List<Notification> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
