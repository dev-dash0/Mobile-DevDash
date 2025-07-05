package com.elfeky.devdash.ui.screens.extra_screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.search.IssueSearch
import com.elfeky.domain.model.search.Search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    uiState: SearchReducer.State,
    focusRequester: FocusRequester,
    onSearchQueryChanged: (String) -> Unit,
    onBackClicked: () -> Unit,
    onSearchBarActiveChanged: (Boolean) -> Unit,
    onSearchSubmitted: () -> Unit,
    onNavigateToCompanyDetails: (id: Int) -> Unit,
    onNavigateToProjectDetails: (id: Int) -> Unit,
    onNavigateToIssueDetails: (id: Int) -> Unit
) {
    Scaffold(
        topBar = {
            SearchTopBar(
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = onSearchQueryChanged,
                onBackClick = onBackClicked,
                searchActive = uiState.searchActive,
                onSearchBarActiveChange = onSearchBarActiveChanged,
                onSearchSubmitted = onSearchSubmitted,
                focusRequester = focusRequester
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (uiState.searchActive) {
                if (uiState.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (uiState.searchResults == null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No results found for \"${uiState.searchQuery}\"",
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        searchResultItem(
                            title = "Issues",
                            results = uiState.searchResults.issues.mapIndexed { index, issue ->
                                SearchItem(
                                    index,
                                    issue.title
                                )
                            },
                            onItemClick = { onNavigateToIssueDetails(it) },
                            showDivider = true,
                        )

                        searchResultItem(
                            title = "Projects",
                            results = uiState.searchResults.projects.map {
                                SearchItem(
                                    it.id,
                                    it.name
                                )
                            },
                            onItemClick = { onNavigateToProjectDetails(it) },
                            showDivider = true,
                        )

                        searchResultItem(
                            title = "Companies",
                            results = uiState.searchResults.tenants.map {
                                SearchItem(
                                    it.id,
                                    it.name
                                )
                            },
                            onItemClick = { onNavigateToCompanyDetails(it) },
                            showDivider = false,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSearchScreenContent() {
    DevDashTheme {
        val mockUiState = SearchReducer.State(
            searchQuery = "sea",
            searchActive = true,
            isLoading = false,
            error = null,
            searchResults = Search(
                listOf(IssueSearch("", "", "", "", "Issue")),
                emptyList(),
                emptyList(),
                emptyList()
            )
        )
        val focusRequester = remember { FocusRequester() }

        SearchScreenContent(
            uiState = mockUiState,
            focusRequester = focusRequester,
            onSearchQueryChanged = {},
            onBackClicked = {},
            onSearchBarActiveChanged = {},
            onSearchSubmitted = {},
            onNavigateToCompanyDetails = {},
            onNavigateToProjectDetails = {},
            onNavigateToIssueDetails = {}
        )
    }
}