package com.elfeky.devdash.ui.screens.extra_screens.notifications

import com.elfeky.domain.model.notification.Notification

data class NotificationUiState(
    val notifications: List<Notification> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
