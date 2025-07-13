package com.elfeky.devdash.ui.screens.extra_screens.notifications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.usecase.notification.GetNotificationsUseCase
import com.elfeky.domain.usecase.notification.MarkNotificationReadUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationsUseCase: GetNotificationsUseCase,
    private val markNotificationReadUseCase: MarkNotificationReadUseCase
) : ViewModel() {
    var state = MutableStateFlow<NotificationUiState>(NotificationUiState())
        private set

    init {
        getNotifications()
    }

    fun getNotifications() {
        viewModelScope.launch {
            notificationsUseCase().collect { result ->
                state.value = when (result) {
                    is Resource.Error -> state.value.copy(error = result.message, isLoading = false)

                    is Resource.Loading -> state.value.copy(isLoading = true)

                    is Resource.Success -> state.value.copy(
                        notifications = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun markAsRead(id: Int) {
        viewModelScope.launch {
            Log.d("NotificationViewModel", "markAsRead called with id: $id")
            markNotificationReadUseCase(id).collect { result ->
                state.value = when (result) {
                    is Resource.Error -> state.value.copy(error = result.message)

                    is Resource.Loading -> state.value

                    is Resource.Success -> state.value.copy(
                        notifications = state.value.notifications.filterNot { it.id == id }
                    )
                }
            }
        }
    }
}