package com.elfeky.devdash.ui.screens.main_screens.home

import com.elfeky.devdash.ui.base.Reducer
import com.elfeky.devdash.ui.screens.main_screens.home.HomeReducer.Effect.ShowSnackbar
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.pin.PinnedItems
import com.elfeky.domain.model.project.Project

class HomeReducer : Reducer<HomeReducer.State, HomeReducer.Event, HomeReducer.Effect> {

    data class State(
        val pinnedItems: PinnedItems? = null,
        val urgentIssues: List<Issue>? = null,
        val projects: List<Project>? = null,
        val isLoadingPinnedItems: Boolean = false,
        val isLoadingUrgentIssues: Boolean = false,
        val error: String? = null
    ) : Reducer.ViewState {
        val isLoading: Boolean
            get() = isLoadingPinnedItems || isLoadingUrgentIssues
    }

    sealed class Event : Reducer.ViewEvent {
        object LoadPinnedItems : Event()
        object LoadUrgentIssues : Event()
        data class PinnedItemsLoaded(val pinnedItems: PinnedItems) : Event()
        data class UrgentIssuesLoaded(val urgentIssues: List<Issue>) : Event()
        data class ProjectsLoaded(val projects: List<Project>) : Event()
        data class PinnedItemsLoadingError(val message: String) : Event()
        data class UrgentIssuesLoadingError(val message: String) : Event()
    }

    sealed class Effect : Reducer.ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }

    override fun reduce(
        previousState: State,
        event: Event
    ): Pair<State, Effect?> {
        return when (event) {
            Event.LoadPinnedItems -> {
                previousState.copy(isLoadingPinnedItems = true, error = null) to null
            }

            Event.LoadUrgentIssues -> {
                previousState.copy(isLoadingUrgentIssues = true, error = null) to null
            }

            is Event.PinnedItemsLoaded -> {
                previousState.copy(
                    pinnedItems = event.pinnedItems,
                    isLoadingPinnedItems = false,
                    error = null
                ) to null
            }

            is Event.UrgentIssuesLoaded -> {
                previousState.copy(
                    urgentIssues = event.urgentIssues,
                    isLoadingUrgentIssues = false,
                    error = null
                ) to null
            }

            is Event.PinnedItemsLoadingError -> {
                previousState.copy(
                    isLoadingPinnedItems = false,
                    error = event.message
                ) to ShowSnackbar(event.message)
            }

            is Event.UrgentIssuesLoadingError -> {
                previousState.copy(
                    isLoadingUrgentIssues = false,
                    error = event.message
                ) to ShowSnackbar(event.message)
            }

            is Event.ProjectsLoaded -> previousState.copy(
                projects = event.projects,
                error = null
            ) to null
        }
    }
}