package com.elfeky.devdash.ui.screens.extra_screens.search

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elfeky.devdash.ui.utils.rememberFlowWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToCompanyDetails: (id: Int) -> Unit,
    onNavigateToProjectDetails: (id: Int) -> Unit,
    onNavigateToIssueDetails: (id: Int) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val uiEffectFlow = rememberFlowWithLifecycle(viewModel.uiEffect)

    LaunchedEffect(Unit) {
        uiEffectFlow.collect { effect ->
            when (effect) {
                is SearchReducer.Effect.ShowSnackbar -> Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()

                SearchReducer.Effect.NavigateBack -> onNavigateBack()
                is SearchReducer.Effect.NavigateToCompanyDetails -> onNavigateToCompanyDetails(
                    effect.companyId
                )

                is SearchReducer.Effect.NavigateToProjectDetails -> onNavigateToProjectDetails(
                    effect.projectId
                )

                is SearchReducer.Effect.NavigateToIssueDetails -> onNavigateToIssueDetails(effect.issueId)
                is SearchReducer.Effect.TriggerSearch -> Unit
            }
        }
    }

    SearchScreenContent(
        uiState = uiState,
        focusRequester = focusRequester,
        onSearchQueryChanged = { viewModel.onEvent(SearchReducer.Event.Update.SearchQuery(it)) },
        onBackClicked = { onNavigateBack() },
        onSearchBarActiveChanged = { viewModel.onEvent(SearchReducer.Event.Update.SearchActive(it)) },
        onSearchSubmitted = { viewModel.onEvent(SearchReducer.Event.TriggerSearch) },
        onNavigateToCompanyDetails = onNavigateToCompanyDetails,
        onNavigateToProjectDetails = onNavigateToProjectDetails,
        onNavigateToIssueDetails = onNavigateToIssueDetails
    )
}