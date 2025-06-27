package com.elfeky.devdash.ui.screens.details_screens.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.dialogs.delete.DeleteConfirmationDialog
import com.elfeky.devdash.ui.common.dialogs.issue.IssueDialog
import com.elfeky.devdash.ui.common.dialogs.project.ProjectDialog
import com.elfeky.devdash.ui.common.dialogs.sprint.SprintDialog
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.common.projectList
import com.elfeky.devdash.ui.common.sprintList
import com.elfeky.devdash.ui.common.tab_row.CustomTabRow
import com.elfeky.devdash.ui.screens.details_screens.components.ScreenContainer
import com.elfeky.devdash.ui.screens.details_screens.project.components.BacklogPage
import com.elfeky.devdash.ui.screens.details_screens.project.components.InfoPage
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.nowLocalDate
import com.elfeky.devdash.ui.utils.toStringDate
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.model.sprint.Sprint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProjectsDetailsContent(
    uiState: ProjectDetailsUiState, // Changed to ProjectDetailsReducer.State
    sprints: List<Sprint>,
    backlogIssues: List<Issue>,
    onEvent: (ProjectDetailsReducer.Event) -> Unit, // Callback for all events
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState, // Passed from higher level
    onNavigateToSprintDetails: (Int) -> Unit, // Navigation effect handled here as callback
    onNavigateBack: () -> Unit // Navigation effect handled here as callback
) {
    val tabs = listOf("Info", "Backlog")
    val scope = rememberCoroutineScope()
    val projectTabsPagerState = rememberPagerState(pageCount = { tabs.size })

    ScreenContainer(
        title = uiState.project?.name ?: "",
        isPinned = uiState.isPinned,
        isOwner = uiState.project?.role == "Admin",
        onPinClick = { onEvent(ProjectDetailsReducer.Event.ProjectAction.PinClicked) },
        onDeleteClick = { onEvent(ProjectDetailsReducer.Event.ProjectAction.DeleteClicked) },
        onEditClick = { onEvent(ProjectDetailsReducer.Event.ProjectAction.EditClicked) },
        onCreateClick = { onEvent(ProjectDetailsReducer.Event.IssueAction.CreateClicked) },
        onBackClick = onNavigateBack,
        modifier = modifier,
        isLoading = uiState.isLoading,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTabRow(
                tabs = tabs,
                selectedTabIndex = projectTabsPagerState.currentPage,
                onTabClick = { index ->
                    scope.launch { projectTabsPagerState.animateScrollToPage(index) }
                }
            )

            HorizontalPager(
                state = projectTabsPagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> InfoPage(uiState) { memberId ->
                        onEvent(
                            ProjectDetailsReducer.Event.RemoveMemberClicked(
                                memberId
                            )
                        )
                    }

                    1 -> {
                        BacklogPage(
                            sprints = sprints,
                            backlogIssues = backlogIssues,
                            pinnedSprints = uiState.pinnedSprints,
                            pinnedIssues = uiState.pinnedIssues,
                            onIssueDroppedOnSprint = { issueId, sprintId ->
                                // You'll need to define an event for this action in your Reducer
                                onEvent(
                                    ProjectDetailsReducer.Event.IssueAction.IssueDroppedOnSprint(
                                        issueId,
                                        sprintId
                                    )
                                )
                            },
                            onCreateSprintClicked = { onEvent(ProjectDetailsReducer.Event.SprintAction.CreateClicked) },
                            onSwipeToDeleteSprint = { sprintId ->
                                onEvent(
                                    ProjectDetailsReducer.Event.SprintAction.SwipedToDelete(
                                        sprintId
                                    )
                                )
                            },
                            onSwipeToPinSprint = { sprintId ->
                                onEvent(
                                    ProjectDetailsReducer.Event.SprintAction.SwipedToPin(
                                        sprintId
                                    )
                                )
                            },
                            onSwipeToDeleteIssue = { issueId ->
                                onEvent(
                                    ProjectDetailsReducer.Event.IssueAction.SwipedToDelete(
                                        issueId
                                    )
                                )
                            },
                            onSwipeToPinIssue = { issueId ->
                                onEvent(ProjectDetailsReducer.Event.IssueAction.SwipedToPin(issueId))
                            },
                            onSprintClicked = { sprintId -> onNavigateToSprintDetails(sprintId) },
//                                onAddIssueClick = { onEvent(ProjectDetailsReducer.Event.IssueAction.CreateClicked) },
                            onIssueClicked = { issue ->
                                onEvent(ProjectDetailsReducer.Event.IssueAction.Clicked(issue))
                            },
                        )
                    }
                }
            }
        }
    }

    uiState.dialogType?.let { dialogType ->
        when (dialogType) {
            ProjectDetailsReducer.DialogType.EditProject -> {
                ProjectDialog(
                    project = uiState.project?.let {
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
                    onDismiss = { onEvent(ProjectDetailsReducer.Event.DismissDialogClicked) },
                    onSubmit = { projectRequest ->
                        onEvent(
                            ProjectDetailsReducer.Event.ProjectAction.ConfirmEditClicked(
                                projectRequest
                            )
                        )
                    }
                )
            }

            ProjectDetailsReducer.DialogType.CreateSprint -> {
                SprintDialog(
                    onDismiss = { onEvent(ProjectDetailsReducer.Event.DismissDialogClicked) },
                    onSubmit = { sprintRequest ->
                        onEvent(
                            ProjectDetailsReducer.Event.SprintAction.ConfirmCreateClicked(
                                sprintRequest
                            )
                        )
                    }
                )
            }

            ProjectDetailsReducer.DialogType.CreateIssue -> {
                IssueDialog(
                    onDismiss = { onEvent(ProjectDetailsReducer.Event.DismissDialogClicked) },
                    onSubmit = { issue ->
                        onEvent(
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
                                    lastUpdate = nowLocalDate(),
                                    sprintId = null
                                )
                            )
                        )
                    },
                    assigneeList = uiState.project?.tenant?.joinedUsers ?: emptyList(),
                    labelList = emptyList(),
                )
            }

            ProjectDetailsReducer.DialogType.DeleteProjectConfirmation -> {
                DeleteConfirmationDialog(
                    title = "Delete Project",
                    text = "Are you sure you want to delete this project? This action cannot be undone.",
                    onConfirm = { onEvent(ProjectDetailsReducer.Event.ProjectAction.ConfirmDeleteClicked) },
                    onDismiss = { onEvent(ProjectDetailsReducer.Event.DismissDialogClicked) }
                )
            }

            is ProjectDetailsReducer.DialogType.DeleteSprintConfirmation -> {
                DeleteConfirmationDialog(
                    title = "Delete Sprint",
                    text = "Are you sure you want to delete this sprint? This action cannot be undone.",
                    onConfirm = {
                        onEvent(
                            ProjectDetailsReducer.Event.SprintAction.ConfirmDeleteClicked(
                                dialogType.sprintId
                            )
                        )
                    },
                    onDismiss = { onEvent(ProjectDetailsReducer.Event.DismissDialogClicked) }
                )
            }

            is ProjectDetailsReducer.DialogType.DeleteIssueConfirmation -> {
                DeleteConfirmationDialog(
                    title = "Delete Issue",
                    text = "Are you sure you want to delete this issue? This action cannot be undone.",
                    onConfirm = {
                        onEvent(
                            ProjectDetailsReducer.Event.IssueAction.ConfirmDeleteClicked(
                                dialogType.issueId
                            )
                        )
                    },
                    onDismiss = { onEvent(ProjectDetailsReducer.Event.DismissDialogClicked) }
                )
            }

            is ProjectDetailsReducer.DialogType.EditIssue -> {}
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
private fun ProjectsDetailsContentPreview() {
    var sprints by remember { mutableStateOf(sprintList) }
    var backlogIssues by remember { mutableStateOf(issueList) }

    DevDashTheme {
        ProjectsDetailsContent(
            uiState = ProjectDetailsUiState(
                project = projectList[0],
                isLoading = false,
                isPinned = true,
                pinnedIssues = emptyList(),
                pinnedSprints = emptyList(),
                dialogType = null,
                onEvent = {}
            ),
            sprints = sprints,
            backlogIssues = backlogIssues,
            onEvent = {},
            snackbarHostState = remember { SnackbarHostState() },
            onNavigateToSprintDetails = {},
            onNavigateBack = {}
        )
    }
}