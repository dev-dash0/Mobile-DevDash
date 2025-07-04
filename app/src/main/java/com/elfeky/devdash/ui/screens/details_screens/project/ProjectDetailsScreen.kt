package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.elfeky.devdash.ui.common.dialogs.delete.DeleteConfirmationDialog
import com.elfeky.devdash.ui.common.dialogs.issue.IssueDialog
import com.elfeky.devdash.ui.common.dialogs.project.ProjectDialog
import com.elfeky.devdash.ui.common.dialogs.sprint.SprintDialog
import com.elfeky.devdash.ui.utils.rememberFlowWithLifecycle
import com.elfeky.devdash.ui.utils.toStringDate
import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.model.project.ProjectRequest
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProjectDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProjectDetailsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToSprintDetails: (Int, String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    val sprints = state.sprintsFlow.collectAsLazyPagingItems()
    val backlogIssues = state.backlogIssuesFlow.collectAsLazyPagingItems()

    val uiEffectFlow = rememberFlowWithLifecycle(viewModel.uiEffect)

    LaunchedEffect(uiEffectFlow) {
        uiEffectFlow.collect { effect ->
            when (effect) {
                ProjectDetailsReducer.Effect.NavigateBack -> onNavigateBack()

                is ProjectDetailsReducer.Effect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true,
                        duration = if (effect.isLong) SnackbarDuration.Long else SnackbarDuration.Short
                    )
                }

                is ProjectDetailsReducer.Effect.NavigateToSprintDetails -> {
                    onNavigateToSprintDetails(effect.sprintId, state.project?.role ?: "Developer")
                }

                is ProjectDetailsReducer.Effect.TriggerLoadSprints -> sprints.refresh()

                is ProjectDetailsReducer.Effect.TriggerLoadBacklogIssues -> backlogIssues.refresh()

                else -> Unit
            }
        }
    }

    ProjectsDetailsContent(
        uiState = state,
        onEvent = viewModel::onEvent,
        snackbarHostState = snackbarHostState,
        onNavigateBack = onNavigateBack,
        sprints = sprints, // Pass LazyPagingItems directly
        backlogIssues = backlogIssues, // Pass LazyPagingItems directly
        modifier = modifier
    )

    state.dialog?.let { dialogType -> // Observe the dialog state directly from reducer
        when (dialogType) {
            ProjectDetailsReducer.DialogType.EditProject -> {
                ProjectDialog(
                    project = state.project?.let {
                        ProjectRequest(
                            name = it.name,
                            description = it.description,
                            creationDate = it.creationDate,
                            startDate = it.startDate,
                            endDate = it.endDate,
                            priority = it.priority,
                            status = it.status
                        )
                    },
                    onDismiss = {
                        viewModel.onEvent(
                            ProjectDetailsReducer.Event.Update.ShowDialog(
                                null
                            )
                        )
                    },
                    onSubmit = { projectRequest ->
                        viewModel.onEvent(
                            ProjectDetailsReducer.Event.ProjectAction.ConfirmEditClicked(
                                projectRequest
                            )
                        )
                    }
                )
            }

            ProjectDetailsReducer.DialogType.CreateSprint -> {
                SprintDialog(
                    onDismiss = {
                        viewModel.onEvent(
                            ProjectDetailsReducer.Event.Update.ShowDialog(
                                null
                            )
                        )
                    },
                    onSubmit = { sprintRequest ->
                        viewModel.onEvent(
                            ProjectDetailsReducer.Event.SprintAction.ConfirmCreateClicked(
                                sprintRequest
                            )
                        )
                    }
                )
            }

            ProjectDetailsReducer.DialogType.CreateIssue -> {
                IssueDialog(
                    onDismiss = {
                        viewModel.onEvent(
                            ProjectDetailsReducer.Event.Update.ShowDialog(
                                null
                            )
                        )
                    },
                    onSubmit = { issue ->
                        viewModel.onEvent(
                            ProjectDetailsReducer.Event.IssueAction.ConfirmCreateClicked(
                                IssueFormFields(
                                    priority = issue.priority.text,
                                    status = issue.status,
                                    title = issue.title,
                                    type = issue.type,
                                    labels = issue.labels.toString(),
                                    description = issue.description,
                                    isBacklog = true,
                                    startDate = issue.startDate!!.toStringDate(),
                                    deadline = issue.deadline!!.toStringDate(),
                                    deliveredDate = null,
                                    lastUpdate = LocalDateTime.now().toString()
                                )
                            )
                        )
                    },
                    assigneeList = state.project?.tenant?.joinedUsers ?: emptyList(),
                )
            }

            ProjectDetailsReducer.DialogType.DeleteProjectConfirmation -> {
                DeleteConfirmationDialog(
                    title = "Delete Project",
                    text = "Are you sure you want to delete this project? This action cannot be undone.",
                    onConfirm = { viewModel.onEvent(ProjectDetailsReducer.Event.ProjectAction.ConfirmDeleteClicked) },
                    onDismiss = {
                        viewModel.onEvent(
                            ProjectDetailsReducer.Event.Update.ShowDialog(
                                null
                            )
                        )
                    }
                )
            }

            is ProjectDetailsReducer.DialogType.DeleteSprintConfirmation -> {
                DeleteConfirmationDialog(
                    title = "Delete Sprint",
                    text = "Are you sure you want to delete this sprint? This action cannot be undone.",
                    onConfirm = {
                        viewModel.onEvent(
                            ProjectDetailsReducer.Event.SprintAction.ConfirmDeleteClicked(
                                dialogType.sprintId
                            )
                        )
                    },
                    onDismiss = {
                        viewModel.onEvent(
                            ProjectDetailsReducer.Event.Update.ShowDialog(
                                null
                            )
                        )
                    }
                )
            }

            is ProjectDetailsReducer.DialogType.DeleteIssueConfirmation -> {
                DeleteConfirmationDialog(
                    title = "Delete Issue",
                    text = "Are you sure you want to delete this issue? This action cannot be undone.",
                    onConfirm = {
                        viewModel.onEvent(
                            ProjectDetailsReducer.Event.IssueAction.ConfirmDeleteClicked(
                                dialogType.issueId
                            )
                        )
                    },
                    onDismiss = {
                        viewModel.onEvent(
                            ProjectDetailsReducer.Event.Update.ShowDialog(
                                null
                            )
                        )
                    }
                )
            }

            is ProjectDetailsReducer.DialogType.EditIssue -> {
                // Handle EditIssue dialog if needed
            }
        }
    }
}
