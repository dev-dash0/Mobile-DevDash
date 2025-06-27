package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.elfeky.devdash.ui.utils.rememberFlowWithLifecycle

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProjectDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProjectDetailsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToSprintDetails: (Int) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    val sprintsLazyItems = state.sprintsFlow?.collectAsLazyPagingItems()
    val backlogIssuesLazyItems = state.backlogIssuesFlow?.collectAsLazyPagingItems()

    var currentDialogType by remember { mutableStateOf<ProjectDetailsReducer.DialogType?>(null) }

    val uiEffectFlow = rememberFlowWithLifecycle(viewModel.uiEffect)

    LaunchedEffect(uiEffectFlow) {
        uiEffectFlow.collect { effect ->
            when (effect) {
                is ProjectDetailsReducer.Effect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        effect.message,
                        duration = if (effect.isLong) SnackbarDuration.Long else SnackbarDuration.Short
                    )
                }

                is ProjectDetailsReducer.Effect.ShowDialog -> currentDialogType = effect.type
                is ProjectDetailsReducer.Effect.DismissDialog -> currentDialogType = null
                ProjectDetailsReducer.Effect.NavigateBack -> onNavigateBack()
                is ProjectDetailsReducer.Effect.NavigateToSprintDetails -> {
                    onNavigateToSprintDetails(effect.sprintId)
                }

                else -> Unit
            }
        }
    }

    val uiState = remember(state, currentDialogType) {
        ProjectDetailsUiState(
            project = state.project,
            isPinned = state.isPinned,
            isLoading = state.isLoading,
            pinnedIssues = state.pinnedIssues,
            pinnedSprints = state.pinnedSprints,
            dialogType = currentDialogType,
            onEvent = viewModel::onEvent
        )
    }

    if (sprintsLazyItems != null && backlogIssuesLazyItems != null) ProjectsDetailsContent(
        uiState = uiState,
        onEvent = viewModel::onEvent, // Pass the ViewModel's onEvent
        snackbarHostState = snackbarHostState,
        onNavigateToSprintDetails = onNavigateToSprintDetails, // Direct navigation callback
        onNavigateBack = onNavigateBack,
        sprints = sprintsLazyItems.itemSnapshotList.items,
        backlogIssues = backlogIssuesLazyItems.itemSnapshotList.items,
        modifier = modifier // Direct navigation callback
    ) else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}