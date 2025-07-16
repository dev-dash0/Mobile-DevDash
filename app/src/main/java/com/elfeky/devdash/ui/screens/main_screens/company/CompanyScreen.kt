package com.elfeky.devdash.ui.screens.main_screens.company

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.elfeky.devdash.ui.common.dialogs.company.CompanyDialog
import com.elfeky.devdash.ui.common.dialogs.delete.DeleteConfirmationDialog
import com.elfeky.devdash.ui.common.dialogs.join.JoinDialog
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
    var showJoinCompanyDialog by remember { mutableStateOf(false) }
    var deleteId by remember { mutableStateOf<Int?>(null) }

    var showFabMenu by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (showFabMenu) 45f else 0f, label = "FAB Rotation"
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimatedVisibility(
                    visible = showFabMenu,
                    enter = fadeIn() + slideInVertically { it },
                    exit = fadeOut() + slideOutVertically { it }
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SmallFloatingActionButton(
                            onClick = {
                                showAddCompanyDialog = true
                                showFabMenu = false
                            },
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Create Company"
                            )
                        }
                        SmallFloatingActionButton(
                            onClick = {
                                showJoinCompanyDialog = true
                                showFabMenu = false
                            },
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "Join Company"
                            )
                        }
                    }
                }

                FloatingActionButton(
                    onClick = { showFabMenu = !showFabMenu },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        modifier = Modifier.rotate(rotationAngle),
                        imageVector = if (showFabMenu) Icons.Filled.Close else Icons.Filled.Add,
                        contentDescription = if (showFabMenu) "Close FAB menu" else "Open FAB menu"
                    )
                }
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
            modifier = Modifier.padding(top = padding.calculateTopPadding()),
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

    if (showJoinCompanyDialog) {
        JoinDialog(
            title = "Join Company",
            onDismiss = { showJoinCompanyDialog = false },
            onConfirm = { code ->
                viewModel.onEvent(CompanyReducer.Event.JoinCompany(code))
                showJoinCompanyDialog = false
            }
        )
    }
}