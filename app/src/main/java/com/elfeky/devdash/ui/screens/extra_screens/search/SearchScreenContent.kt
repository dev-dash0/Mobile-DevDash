package com.elfeky.devdash.ui.screens.extra_screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.elfeky.devdash.ui.common.card.CompanySearchCard
import com.elfeky.devdash.ui.common.card.IssueSearchCard
import com.elfeky.devdash.ui.common.card.ProjectSearchCard
import com.elfeky.devdash.ui.common.companySearchList
import com.elfeky.devdash.ui.common.issueSearchList
import com.elfeky.devdash.ui.common.projectSearchList
import com.elfeky.devdash.ui.screens.extra_screens.search.components.SearchTopBar
import com.elfeky.devdash.ui.screens.extra_screens.search.components.searchResultItem
import com.elfeky.devdash.ui.theme.DevDashTheme
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
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.searchResults != null
                && uiState.searchResults.issues.isEmpty()
                && uiState.searchResults.tenants.isEmpty()
                && uiState.searchResults.projects.isEmpty()
                && uiState.searchResults.sprints.isEmpty()
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No results found for \"${uiState.searchQuery}\"",
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else if (uiState.searchResults != null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    searchResultItem(
                        title = "Issues",
                        results = uiState.searchResults.issues,
                        showDivider = true,
                    ) { issue ->
                        IssueSearchCard(
                            issue = issue,
                            modifier = Modifier
                                .fillMaxWidth(.9f)
                                .clickable { onNavigateToIssueDetails(1) }
                        )
                    }

                    searchResultItem(
                        title = "Projects",
                        results = uiState.searchResults.projects,
                        key = { it.projectCode },
                        showDivider = true,
                    ) { project ->
                        ProjectSearchCard(
                            project = project,
                            modifier = Modifier
                                .fillMaxWidth(.9f)
                                .clickable { onNavigateToProjectDetails(project.id) }
                        )
                    }

                    searchResultItem(
                        title = "Companies",
                        results = uiState.searchResults.tenants,
                        key = { it.tenantCode },
                        showDivider = false,
                    ) { company ->
                        CompanySearchCard(
                            tenant = company,
                            modifier = Modifier
                                .fillMaxWidth(.9f)
                                .clickable { onNavigateToCompanyDetails(company.id) }
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
                issueSearchList,
                projectSearchList,
                emptyList(),
                companySearchList
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