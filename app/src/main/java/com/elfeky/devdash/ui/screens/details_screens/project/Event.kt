package com.elfeky.devdash.ui.screens.details_screens.project

sealed class Event {
    data class ShowError(val message: String) : Event()
    data object ProjectCreated : Event()
    data object HideCreateDialog : Event()
}