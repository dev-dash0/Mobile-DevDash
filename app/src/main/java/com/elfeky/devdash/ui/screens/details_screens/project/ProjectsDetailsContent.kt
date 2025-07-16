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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.common.projectList
import com.elfeky.devdash.ui.common.sprintList
import com.elfeky.devdash.ui.common.tab_row.CustomTabRow
import com.elfeky.devdash.ui.screens.details_screens.components.ScreenContainer
import com.elfeky.devdash.ui.screens.details_screens.project.components.BacklogPage
import com.elfeky.devdash.ui.screens.details_screens.project.components.InfoPage
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.sprint.Sprint
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProjectsDetailsContent(
    uiState: ProjectDetailsReducer.State,
    sprints: LazyPagingItems<Sprint>,
    backlogIssues: LazyPagingItems<Issue>,
    onEvent: (ProjectDetailsReducer.Event) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit
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
        snackbarHostState = snackbarHostState,
        onInviteClick = { onEvent(ProjectDetailsReducer.Event.ProjectAction.InviteMemberClicked) }
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
                            onSprintClicked = { sprintId ->
                                onEvent(
                                    ProjectDetailsReducer.Event.SprintAction.Clicked(
                                        sprintId
                                    )
                                )
                            },
                            onIssueClicked = { issue ->
                                onEvent(ProjectDetailsReducer.Event.IssueAction.Clicked(issue))
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
private fun ProjectsDetailsContentPreview() {
    var sprints = flowOf(PagingData.from(sprintList)).collectAsLazyPagingItems()
    var backlogIssues = flowOf(PagingData.from(issueList)).collectAsLazyPagingItems()

    DevDashTheme {
        ProjectsDetailsContent(
            uiState = ProjectDetailsReducer.State(
                project = projectList[0],
                isLoading = false,
                isPinned = true,
                pinnedIssues = emptyList(),
                pinnedSprints = emptyList()
            ),
            sprints = sprints,
            backlogIssues = backlogIssues,
            onEvent = {},
            snackbarHostState = remember { SnackbarHostState() },
            onNavigateBack = {}
        )
    }
}