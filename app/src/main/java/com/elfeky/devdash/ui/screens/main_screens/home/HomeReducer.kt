package com.elfeky.devdash.ui.screens.main_screens.home

import com.elfeky.devdash.ui.base.Reducer
import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.pin.PinnedItems
import com.elfeky.domain.model.project.Project

class HomeReducer : Reducer<HomeReducer.HomeState, HomeReducer.Event, HomeReducer.Effect> {

    data class HomeState(
        val user: UserProfile? = null,
        val pinnedItems: PinnedItems? = null,
        val urgentIssues: List<Issue>? = null,
        val projects: List<Project>? = null,
        val isLoadingUser: Boolean = false,
        val isLoadingPinnedItems: Boolean = false,
        val isLoadingUrgentIssues: Boolean = false,
        val error: String? = null
    ) : Reducer.ViewState {
        val isLoading: Boolean
            get() = isLoadingUser || isLoadingPinnedItems || isLoadingUrgentIssues
    }

    sealed class Event : Reducer.ViewEvent {
        object LoadUser : Event()
        object LoadPinnedItems : Event()
        object LoadUrgentIssues : Event()
        data class UserLoaded(val user: UserProfile) : Event()
        data class PinnedItemsLoaded(val pinnedItems: PinnedItems) : Event()
        data class UrgentIssuesLoaded(val urgentIssues: List<Issue>) : Event()
        data class ProjectsLoaded(val projects: List<Project>) : Event()
        data class UserLoadingError(val message: String) : Event()
        data class PinnedItemsLoadingError(val message: String) : Event()
        data class UrgentIssuesLoadingError(val message: String) : Event()
    }

    sealed class Effect : Reducer.ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }

    override fun reduce(
        previousState: HomeState,
        event: Event
    ): Pair<HomeState, Effect?> {
        return when (event) {
            Event.LoadUser -> previousState.copy(isLoadingUser = true, error = null) to null
            Event.LoadPinnedItems -> previousState.copy(
                isLoadingPinnedItems = true,
                error = null
            ) to null

            Event.LoadUrgentIssues -> previousState.copy(
                isLoadingUrgentIssues = true,
                error = null
            ) to null

            is Event.UserLoaded -> previousState.copy(
                user = event.user,
                isLoadingUser = false,
                error = null
            ) to null

            is Event.PinnedItemsLoaded -> previousState.copy(
                pinnedItems = event.pinnedItems,
                isLoadingPinnedItems = false,
                error = null
            ) to null

            is Event.UrgentIssuesLoaded -> previousState.copy(
                urgentIssues = event.urgentIssues,
                isLoadingUrgentIssues = false,
                error = null
            ) to null

            is Event.UserLoadingError -> previousState.copy(
                isLoadingUser = false,
                error = event.message
            ) to Effect.ShowSnackbar(event.message)

            is Event.PinnedItemsLoadingError -> previousState.copy(
                isLoadingPinnedItems = false,
                error = event.message
            ) to Effect.ShowSnackbar(event.message)

            is Event.UrgentIssuesLoadingError -> previousState.copy(
                isLoadingUrgentIssues = false,
                error = event.message
            ) to Effect.ShowSnackbar(event.message)

            is Event.ProjectsLoaded -> previousState.copy(
                projects = event.projects,
                error = null
            ) to null
        }
    }
}