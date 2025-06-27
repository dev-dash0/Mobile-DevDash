package com.elfeky.devdash.ui.screens.details_screens.company

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elfeky.devdash.ui.common.dialogs.company.CompanyDialog
import com.elfeky.devdash.ui.common.dialogs.company.model.CompanyUiModel
import com.elfeky.devdash.ui.common.dialogs.delete.DeleteConfirmationDialog
import com.elfeky.devdash.ui.common.dialogs.project.ProjectDialog
import com.elfeky.devdash.ui.utils.rememberFlowWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: CompanyDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNavigateToProject: (id: Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var currentDialogType by remember { mutableStateOf<CompanyDetailsReducer.DialogType?>(null) }

    val uiEffectFlow = rememberFlowWithLifecycle(viewModel.uiEffect)

    LaunchedEffect(uiEffectFlow) {
        uiEffectFlow.collect { effect ->
            when (effect) {
                CompanyDetailsReducer.Effect.NavigateBack -> onBackClick()
                is CompanyDetailsReducer.Effect.NavigateToProjectDetails -> onNavigateToProject(
                    effect.projectId
                )

                is CompanyDetailsReducer.Effect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }

                is CompanyDetailsReducer.Effect.ShowDialog -> {
                    currentDialogType = effect.type
                }

                else -> Unit
            }
        }
    }

    CompanyDetailsContent(
        uiState = state,
        onEvent = viewModel::onEvent,
        modifier = modifier.fillMaxSize(),
        snackbarHostState = snackbarHostState
    )

    currentDialogType?.let { dialogType ->
        when (dialogType) {
            CompanyDetailsReducer.DialogType.EditCompany -> {
                CompanyDialog(
                    onDismiss = {
                        currentDialogType = null
                        viewModel.onEvent(CompanyDetailsReducer.Event.DismissDialogClicked)
                    },
                    onSubmit = { updatedCompanyUiModel ->
                        viewModel.onEvent(
                            CompanyDetailsReducer.Event.CompanyAction.ConfirmEditClicked(
                                updatedCompanyUiModel
                            )
                        )
                        currentDialogType = null
                    },
                    company = state.tenant?.let {
                        CompanyUiModel(
                            title = it.name,
                            websiteUrl = it.tenantUrl,
                            keywords = it.keywords,
                            description = it.description,
                            logoUri = it.image
                        )
                    }
                )
            }

            CompanyDetailsReducer.DialogType.CreateProject -> {
                ProjectDialog(
                    onDismiss = {
                        currentDialogType = null
                        viewModel.onEvent(CompanyDetailsReducer.Event.DismissDialogClicked)
                    },
                    onSubmit = { projectRequest ->
                        viewModel.onEvent(
                            CompanyDetailsReducer.Event.ProjectAction.ConfirmCreateClicked(
                                projectRequest
                            )
                        )
                        currentDialogType = null
                    },
                )
            }

            CompanyDetailsReducer.DialogType.DeleteCompanyConfirmation -> {
                DeleteConfirmationDialog(
                    title = "Delete Company",
                    text = "Are you sure you want to delete this company? This action cannot be undone.",
                    onDismiss = {
                        currentDialogType = null
                        viewModel.onEvent(CompanyDetailsReducer.Event.DismissDialogClicked)
                    },
                    onConfirm = {
                        viewModel.onEvent(CompanyDetailsReducer.Event.CompanyAction.ConfirmDeleteClicked)
                        currentDialogType = null
                    }
                )
            }

            is CompanyDetailsReducer.DialogType.DeleteProjectConfirmation -> {
                DeleteConfirmationDialog(
                    title = "Delete Project",
                    text = "Are you sure you want to delete this project? This action cannot be undone.",
                    onDismiss = {
                        currentDialogType = null
                        viewModel.onEvent(CompanyDetailsReducer.Event.DismissDialogClicked)
                    },
                    onConfirm = {
                        viewModel.onEvent(
                            CompanyDetailsReducer.Event.ProjectAction.ConfirmDeleteClicked(
                                dialogType.projectId
                            )
                        )
                        currentDialogType = null
                    }
                )
            }
        }
    }
}
