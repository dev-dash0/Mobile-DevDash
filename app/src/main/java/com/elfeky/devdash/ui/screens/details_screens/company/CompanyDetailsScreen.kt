package com.elfeky.devdash.ui.screens.details_screens.company

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.elfeky.devdash.ui.common.dialogs.join.InviteDialog
import com.elfeky.devdash.ui.common.dialogs.join.JoinDialog
import com.elfeky.devdash.ui.common.dialogs.project.ProjectDialog
import com.elfeky.devdash.ui.screens.details_screens.company.components.chat_bot.ChatScreen
import com.elfeky.devdash.ui.screens.details_screens.company.components.chat_bot.ChatViewModel
import com.elfeky.devdash.ui.utils.rememberFlowWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsScreen(
    modifier: Modifier = Modifier,
    companyViewModel: CompanyDetailsViewModel = hiltViewModel(),
    chatViewModel: ChatViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNavigateToProject: (id: Int) -> Unit
) {
    val state by companyViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var currentDialogType by remember { mutableStateOf<CompanyDetailsReducer.DialogType?>(null) }

    val uiEffectFlow = rememberFlowWithLifecycle(companyViewModel.uiEffect)

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
        onEvent = companyViewModel::onEvent,
        modifier = modifier.fillMaxSize(),
        snackbarHostState = snackbarHostState
    )

    currentDialogType?.let { dialogType ->
        when (dialogType) {
            CompanyDetailsReducer.DialogType.EditCompany -> {
                CompanyDialog(
                    onDismiss = {
                        currentDialogType = null
                        companyViewModel.onEvent(CompanyDetailsReducer.Event.DismissDialogClicked)
                    },
                    onSubmit = { updatedCompanyUiModel ->
                        companyViewModel.onEvent(
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
                        companyViewModel.onEvent(CompanyDetailsReducer.Event.DismissDialogClicked)
                    },
                    onSubmit = { projectRequest ->
                        companyViewModel.onEvent(
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
                        companyViewModel.onEvent(CompanyDetailsReducer.Event.DismissDialogClicked)
                    },
                    onConfirm = {
                        companyViewModel.onEvent(CompanyDetailsReducer.Event.CompanyAction.ConfirmDeleteClicked)
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
                        companyViewModel.onEvent(CompanyDetailsReducer.Event.DismissDialogClicked)
                    },
                    onConfirm = {
                        companyViewModel.onEvent(
                            CompanyDetailsReducer.Event.ProjectAction.ConfirmDeleteClicked(
                                dialogType.projectId
                            )
                        )
                        currentDialogType = null
                    }
                )
            }

            is CompanyDetailsReducer.DialogType.InviteMember -> {
                InviteDialog(
                    onDismiss = { currentDialogType = null },
                    onConfirm = { email, role ->
                        companyViewModel.onEvent(
                            CompanyDetailsReducer.Event.ConfirmInviteMemberClicked(
                                email,
                                role
                            )
                        )
                        currentDialogType = null
                    }
                )
            }

            is CompanyDetailsReducer.DialogType.JoinProject -> {
                JoinDialog(
                    title = "Join Project",
                    onDismiss = { currentDialogType = null },
                    onConfirm = { code ->
                        companyViewModel.onEvent(
                            CompanyDetailsReducer.Event.ConfirmJoinProjectClicked(code)
                        )
                        currentDialogType = null
                    }
                )
            }
        }
    }

    if (state.isShowBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = {
                companyViewModel.onEvent(
                    CompanyDetailsReducer.Event.Update.IsShowingBottomSheet(
                        false
                    )
                )
            },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            ChatScreen(viewModel = chatViewModel)
        }
    }
}