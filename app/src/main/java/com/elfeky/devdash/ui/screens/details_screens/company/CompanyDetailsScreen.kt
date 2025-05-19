package com.elfeky.devdash.ui.screens.details_screens.company

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elfeky.domain.model.tenant.TenantRequest
import kotlinx.coroutines.flow.last

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: CompanyDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNavigateToProject: (id: Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isDelete by remember { mutableStateOf(false) }

    LaunchedEffect(isDelete) {
        if (isDelete) {
            if (viewModel.deleteCompany().last())
                onBackClick()
            else isDelete = false
        }
    }

    CompanyDetailsContent(
        state = state,
        onRemoveMemberClick = viewModel::removeMember,
        onDeleteClick = { isDelete = true },
        onPinClick = viewModel::pinTenant,
        onBackClick = onBackClick,
        onEditConfirm = {
            viewModel.updateCompany(
                TenantRequest(
                    it.tenant!!.description,
                    it.tenant.image,
                    it.tenant.keywords,
                    it.tenant.name,
                    it.tenant.tenantUrl
                )
            )
        },
        onCreateProject = viewModel::addProject,
        onProjectClick = onNavigateToProject,
        onProjectSwipeToDelete = viewModel::deleteProject,
        onProjectSwipeToPin = viewModel::pinProject,
        modifier = modifier,
    )
}