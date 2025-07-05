package com.elfeky.devdash.ui.screens.extra_screens.search

import androidx.lifecycle.viewModelScope
import com.elfeky.devdash.ui.base.BaseViewModel
import com.elfeky.domain.usecase.search.SearchUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
//    private val recentSearchesRepository: RecentSearchesRepository,
) : BaseViewModel<SearchReducer.State, SearchReducer.Event, SearchReducer.Effect>(
    SearchReducer.initialState,
    SearchReducer()
) {

    init {
        observeEffects()
//        loadRecentSearches()
    }

    fun onEvent(event: SearchReducer.Event) {
        when (event) {
            is SearchReducer.Event.Update,
            is SearchReducer.Event.SearchErrorDismissed -> sendEvent(event)

            else -> sendEventForEffect(event)
        }
    }

    private fun observeEffects() {
        viewModelScope.launch {
            internalEffect.collect { effect ->
                when (effect) {
                    is SearchReducer.Effect.TriggerSearch -> performSearch(effect.query)
//                    is SearchReducer.Effect.SaveRecentSearch -> saveRecentSearch(effect.searchResult)
//                    SearchReducer.Effect.ClearRecentSearches -> clearRecentSearches()
                    SearchReducer.Effect.NavigateBack,
                    is SearchReducer.Effect.ShowSnackbar,
                    is SearchReducer.Effect.NavigateToCompanyDetails, // If applicable
                    is SearchReducer.Effect.NavigateToProjectDetails, // If applicable
                    is SearchReducer.Effect.NavigateToIssueDetails -> sendUiEffect(effect) // If applicable
                }
            }
        }
    }

//    private fun loadRecentSearches() {
//        viewModelScope.launch {
//            recentSearchesRepository.getRecentSearches().collect { searches ->
//                sendEvent(SearchReducer.Event.Update.RecentSearches(searches))
//            }
//        }
//    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            sendEvent(SearchReducer.Event.Update.IsLoading(true))
            searchUseCase(query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        sendEvent(SearchReducer.Event.Update.SearchResults(result.data))
//                        result.data?.let {
//                            sendInternalEffect(SearchReducer.Effect.SaveRecentSearch(it))
//                        }
                    }

                    is Resource.Error -> {
                        sendEvent(
                            SearchReducer.Event.Error.SearchLoadError(
                                result.message ?: "Unknown error"
                            )
                        )
                    }

                    is Resource.Loading -> Unit // Handled by initial isLoading(true)
                }
            }
            sendEvent(SearchReducer.Event.Update.IsLoading(false))
        }
    }

//    private fun saveRecentSearch(search: Search) {
//        viewModelScope.launch {
//            recentSearchesRepository.addRecentSearch(search)
//            loadRecentSearches() // Reload to update the UI
//        }
//    }
//
//    private fun clearRecentSearches() {
//        viewModelScope.launch {
//            recentSearchesRepository.clearRecentSearches()
//            sendEvent(SearchReducer.Event.Update.RecentSearches(emptyList()))
//        }
//    }
}