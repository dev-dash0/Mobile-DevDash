package com.elfeky.devdash.ui.screens.details_screens.company

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: CompanyDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNavigateToProject: (id: Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) {
            onBackClick()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }
    LaunchedEffect(state.deleteErrorMessage) {
        state.deleteErrorMessage?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    CompanyDetailsContent(
        state = state,
        onRemoveMemberClick = viewModel::removeMember,
        onDeleteClick = viewModel::deleteCompany,
        onPinClick = viewModel::pinTenant,
        onBackClick = onBackClick,
        onEditConfirm = viewModel::updateCompany,
        onCreateProject = viewModel::addProject,
        onProjectClick = onNavigateToProject,
        onProjectSwipeToDelete = viewModel::deleteProject,
        onProjectSwipeToPin = viewModel::pinProject,
        modifier = modifier,
    )
}