package com.elfeky.devdash.ui.screens.main_screens.company

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.elfeky.devdash.ui.common.dialogs.company.CompanyDialog
import com.elfeky.devdash.ui.common.dialogs.delete.DeleteConfirmationDialog
import com.elfeky.domain.model.tenant.TenantRequest

@Composable
fun CompanyScreen(
    onNavigateToCompanyDetails: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CompanyViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val companies = state.companies.collectAsLazyPagingItems()
    var showAddCompanyDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var deleteId by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddCompanyDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Create")
            }
        }
    ) { padding ->
        CompanyContent(
            state = state,
            companies = companies,
            onEvent = viewModel::onEvent,
            onDeleteCompany = {
                deleteId = it
                showDeleteDialog = true
            },
            onCompanyClick = { id -> viewModel.onEvent(CompanyReducer.Event.CompanyClicked(id)) },
            modifier = Modifier.padding(padding),
        )
    }

    viewModel.uiEffect.collectAsStateWithLifecycle(initialValue = null).value?.let { effect ->
        Log.d("CompanyScreen", "uiEffect: $effect")
        when (effect) {
            is CompanyReducer.Effect.NavigateToCompanyDetails -> onNavigateToCompanyDetails(effect.companyId)
            CompanyReducer.Effect.RefreshCompanyList -> companies.refresh()

            else -> Unit
        }
    }

    if (showAddCompanyDialog) {
        CompanyDialog(
            onDismiss = { showAddCompanyDialog = false },
            onSubmit = {
                viewModel.onEvent(
                    CompanyReducer.Event.AddCompany(
                        TenantRequest(
                            description = it.description,
                            image = it.logoUri.toString(),
                            keywords = it.keywords,
                            name = it.title,
                            tenantUrl = it.websiteUrl
                        )
                    )
                )
                showAddCompanyDialog = false
            }
        )
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            title = "Delete Company",
            text = "Are you sure to delete it?",
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                viewModel.onEvent(CompanyReducer.Event.DeleteCompany(deleteId!!))
                showDeleteDialog = false
                deleteId = null
            }
        )
    }
}