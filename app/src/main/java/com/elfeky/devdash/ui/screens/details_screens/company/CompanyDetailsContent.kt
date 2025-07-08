package com.elfeky.devdash.ui.screens.details_screens.company

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.FilterChipRow
import com.elfeky.devdash.ui.common.projectList
import com.elfeky.devdash.ui.common.tab_row.CustomTabRow
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.screens.details_screens.company.components.CompanyInfoPage
import com.elfeky.devdash.ui.screens.details_screens.company.components.ProjectList
import com.elfeky.devdash.ui.screens.details_screens.components.ScreenContainer
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.tenant.Tenant
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CompanyDetailsContent(
    uiState: CompanyDetailsReducer.State,
    onEvent: (CompanyDetailsReducer.Event) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val tabs = listOf("Info", "Projects")
    val scope = rememberCoroutineScope()

    val companyTabsPagerState = rememberPagerState(pageCount = { tabs.size })

    ScreenContainer(
        snackbarHostState = snackbarHostState,
        title = uiState.tenant?.name ?: "",
        isPinned = uiState.isPinned,
        isOwner = uiState.tenant?.role == "Admin",
        onPinClick = {
            onEvent(CompanyDetailsReducer.Event.PinCompanyClicked)
            Log.d("CompanyDetails", "isPinned: " + uiState.isPinned.toString())
        },
        onDeleteClick = { onEvent(CompanyDetailsReducer.Event.CompanyAction.DeleteClicked) },
        onEditClick = { onEvent(CompanyDetailsReducer.Event.CompanyAction.EditClicked) },
        onBackClick = { onEvent(CompanyDetailsReducer.Event.BackClicked) },
        modifier = modifier,
        isLoading = uiState.isLoading && uiState.tenant == null,
        image = uiState.tenant?.image,
        hasImageBackground = true,
        onCreateClick = { onEvent(CompanyDetailsReducer.Event.ProjectAction.CreateClicked) },
        scrollBehavior = scrollBehavior
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTabRow(
                tabs = tabs,
                selectedTabIndex = companyTabsPagerState.currentPage,
                onTabClick = { index ->
                    scope.launch { companyTabsPagerState.animateScrollToPage(index) }
                }
            )

            HorizontalPager(
                state = companyTabsPagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> CompanyInfoPage(
                        tenant = uiState.tenant,
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection)
                            .verticalScroll(rememberScrollState()),
                        onRemoveMemberClick = { memberId ->
                            onEvent(CompanyDetailsReducer.Event.RemoveMemberClicked(memberId))
                        },
                        onCopyTextClicked = { onEvent(CompanyDetailsReducer.Event.CopyTextClicked(it)) }
                    )

                    1 -> CompanyProjectsTab(
                        uiState = uiState,
                        onEvent = onEvent,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun CompanyProjectsTab(
    uiState: CompanyDetailsReducer.State,
    onEvent: (CompanyDetailsReducer.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    val choices = listOf("All", "Owned", "Joined")
    val scope = rememberCoroutineScope()
    val projectPagerState = rememberPagerState(pageCount = { choices.size })

    val projectsToShow by remember(
        uiState.projects,
        projectPagerState.currentPage,
        uiState.userId
    ) {
        derivedStateOf {
            when (projectPagerState.currentPage) {
                0 -> uiState.projects
                1 -> uiState.projects.filter { it.role == "Admin" }
                2 -> uiState.projects.filterNot { it.role == "Admin" }
                else -> emptyList()
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FilterChipRow(
            choices = choices,
            initialSelectedIndex = projectPagerState.currentPage,
            onChoiceSelected = { index ->
                scope.launch { projectPagerState.animateScrollToPage(index) }
            }
        )

        HorizontalPager(
            state = projectPagerState,
            userScrollEnabled = false,
            key = { choices[it] }
        ) {
            ProjectList(
                projects = projectsToShow,
                pinnedProjects = uiState.pinnedProjects,
                onProjectClick = { onEvent(CompanyDetailsReducer.Event.ProjectClicked(it)) },
                onProjectSwipeToDelete = { projectId ->
                    onEvent(
                        CompanyDetailsReducer.Event.ProjectAction.SwipedToDelete(
                            projectId
                        )
                    )
                },
                onProjectSwipeToPin = { projectId ->
                    onEvent(
                        CompanyDetailsReducer.Event.ProjectAction.SwipedToPin(
                            projectId
                        )
                    )
                }
            )
        }
    }
}

@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun CompanyDetailsContentPreview() {
    var uiState by remember {
        mutableStateOf(
            CompanyDetailsReducer.initialState.copy(
                tenant = Tenant(
                    id = 5,
                    name = "Acme Corporation",
                    image = null,
                    tenantCode = "ACME-CORP",
                    tenantUrl = "https://acmecorp.com",
                    joinedUsers = userList,
                    owner = userList[0],
                    keywords = "Manufacturing,Innovation,Technology",
                    description = "Acme Corporation is a leading global innovator in high-quality, cutting-edge products and solutions across diverse industries. We are committed to excellence, sustainability, and transforming the future through our relentless pursuit of innovation.",
                    ownerID = 1,
                    role = "Admin"
                ),
                projects = projectList,
                pinnedProjects = listOf(projectList[0]),
                userId = 1,
                isLoading = false,
                projectsLoading = false,
                isPinned = true,
            )
        )
    }

    DevDashTheme {
        CompanyDetailsContent(
            uiState = uiState,
            onEvent = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}
