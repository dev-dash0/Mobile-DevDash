package com.elfeky.devdash.ui.screens.extra_screens.search

import androidx.lifecycle.viewModelScope
import com.elfeky.devdash.ui.base.BaseViewModel
import com.elfeky.domain.model.search.Search
import com.elfeky.domain.usecase.search.SearchUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
) : BaseViewModel<SearchReducer.State, SearchReducer.Event, SearchReducer.Effect>(
    SearchReducer.initialState,
    SearchReducer()
) {
    init {
        observeEffects()
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
                    SearchReducer.Effect.NavigateBack,
                    is SearchReducer.Effect.ShowSnackbar,
                    is SearchReducer.Effect.NavigateToCompanyDetails,
                    is SearchReducer.Effect.NavigateToProjectDetails,
                    is SearchReducer.Effect.NavigateToIssueDetails -> sendUiEffect(effect)
                }
            }
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            onEvent(SearchReducer.Event.Update.IsLoading(true))
            searchUseCase(query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        onEvent(
                            SearchReducer.Event.Update.SearchResults(
                                result.data ?: Search(
                                    issues = emptyList(),
                                    projects = emptyList(),
                                    sprints = emptyList(),
                                    tenants = emptyList()
                                )
                            )
                        )
                    }

                    is Resource.Error -> {
                        onEvent(
                            SearchReducer.Event.Error.SearchLoadError(
                                result.message ?: "Unknown error"
                            )
                        )
                    }

                    is Resource.Loading -> Unit
                }
            }
            onEvent(SearchReducer.Event.Update.IsLoading(false))
        }
    }
}