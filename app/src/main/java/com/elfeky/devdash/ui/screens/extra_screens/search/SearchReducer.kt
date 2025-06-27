package com.elfeky.devdash.ui.screens.extra_screens.search

import com.elfeky.devdash.ui.base.Reducer
import com.elfeky.devdash.ui.screens.extra_screens.search.SearchReducer.Effect.NavigateToCompanyDetails
import com.elfeky.devdash.ui.screens.extra_screens.search.SearchReducer.Effect.NavigateToIssueDetails
import com.elfeky.devdash.ui.screens.extra_screens.search.SearchReducer.Effect.NavigateToProjectDetails
import com.elfeky.devdash.ui.screens.extra_screens.search.SearchReducer.Effect.ShowSnackbar
import com.elfeky.devdash.ui.screens.extra_screens.search.SearchReducer.Effect.TriggerSearch
import com.elfeky.domain.model.search.Search
import javax.annotation.concurrent.Immutable


class SearchReducer : Reducer<SearchReducer.State, SearchReducer.Event, SearchReducer.Effect> {
    override fun reduce(
        previousState: State,
        event: Event
    ): Pair<State, Effect?> {
        return when (event) {
            is Event.Update.SearchQuery -> previousState.copy(searchQuery = event.query) to null
            is Event.Update.SearchActive -> previousState.copy(searchActive = event.active) to null
            is Event.Update.SearchResults -> previousState.copy(searchResults = event.results) to null
//            is Event.Update.RecentSearches -> previousState.copy(recentSearches = event.searches) to null
            is Event.Update.IsLoading -> previousState.copy(isLoading = event.loading) to null
            is Event.Error.SearchLoadError -> previousState.copy(error = event.message) to ShowSnackbar(
                event.message
            )

            Event.SearchErrorDismissed -> previousState.copy(error = null) to null

            is Event.TriggerSearch -> previousState to TriggerSearch(previousState.searchQuery)
            is Event.ClearSearch -> previousState.copy(
                searchQuery = "",
                searchResults = null
            ) to null

//            is Event.SelectRecentSearch -> previousState.copy(searchQuery = event.query) to Effect.TriggerSearch(
//                event.query
//            )

//            Event.ClearAllRecentSearchesClicked -> previousState to Effect.ClearRecentSearches
            is Event.SearchResultClicked.Company -> previousState to NavigateToCompanyDetails(
                event.companyId
            )

            is Event.SearchResultClicked.Project -> previousState to NavigateToProjectDetails(
                event.projectId
            )

            is Event.SearchResultClicked.Issue -> previousState to NavigateToIssueDetails(
                event.issueId
            )

            Event.BackClicked -> previousState to Effect.NavigateBack
        }
    }

    // View State
    @Immutable
    data class State(
        val searchQuery: String = "",
        val searchActive: Boolean = false,
        val searchResults: Search? = null,
//        val recentSearches: List<Search> = emptyList(), // Store recent search objects
        val isLoading: Boolean = false,
        val error: String? = null
    ) : Reducer.ViewState

    // View Events
    sealed class Event : Reducer.ViewEvent {
        sealed class Update : Event() {
            data class SearchQuery(val query: String) : Update()
            data class SearchActive(val active: Boolean) : Update()
            data class SearchResults(val results: Search?) : Update()

            //            data class RecentSearches(val searches: List<Search>) : Update()
            data class IsLoading(val loading: Boolean) : Update()
        }

        sealed class Error : Event() {
            data class SearchLoadError(val message: String) : Error()
        }

        // User actions
        object TriggerSearch : Event()
        object ClearSearch : Event()
        data object BackClicked : Event()
//        data class SelectRecentSearch(val query: String) : Event()
//        object ClearAllRecentSearchesClicked : Event()

        // When a search result is clicked (assuming navigation)
        sealed class SearchResultClicked : Event() {
            data class Company(val companyId: Int) : SearchResultClicked()
            data class Project(val projectId: Int) : SearchResultClicked()
            data class Issue(val issueId: Int) : SearchResultClicked()
        }

        object SearchErrorDismissed : Event()
    }

    // View Effects
    sealed class Effect : Reducer.ViewEffect {
        data class TriggerSearch(val query: String) : Effect()

        //        data class SaveRecentSearch(val searchResult: Search) : Effect()
//        object ClearRecentSearches : Effect()
        data object NavigateBack : Effect()

        // UI Effects (e.g., showing a Snackbar, navigation)
        data class ShowSnackbar(val message: String) : Effect()
        data class NavigateToCompanyDetails(val companyId: Int) : Effect()
        data class NavigateToProjectDetails(val projectId: Int) : Effect()
        data class NavigateToIssueDetails(val issueId: Int) : Effect()
    }

    companion object {
        val initialState = State()
    }
}