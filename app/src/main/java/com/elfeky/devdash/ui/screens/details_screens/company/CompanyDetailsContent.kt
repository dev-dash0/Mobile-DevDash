package com.elfeky.devdash.ui.screens.details_screens.company

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.elfeky.devdash.ui.common.dialogs.company.CompanyDialog
import com.elfeky.devdash.ui.common.dialogs.company.model.CompanyUiModel
import com.elfeky.devdash.ui.common.dialogs.project.ProjectDialog
import com.elfeky.devdash.ui.common.dialogs.userList
import com.elfeky.devdash.ui.common.tab_row.CustomTabRow
import com.elfeky.devdash.ui.screens.details_screens.company.components.CompanyInfoPage
import com.elfeky.devdash.ui.screens.details_screens.company.components.ProjectList
import com.elfeky.devdash.ui.screens.details_screens.company.components.SingleSelectChipRow
import com.elfeky.devdash.ui.screens.details_screens.components.ScreenContainer
import com.elfeky.devdash.ui.screens.details_screens.components.projectList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.model.tenant.Tenant
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsContent(
    state: CompanyDetailsUiState,
    onRemoveMemberClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onPinClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditConfirm: (companyUiModel: CompanyUiModel) -> Unit,
    onCreateProject: (project: ProjectRequest) -> Unit,
    onProjectClick: (id: Int) -> Unit,
    onProjectSwipeToDelete: (id: Int) -> Unit,
    onProjectSwipeToPin: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = listOf("Info", "Projects")
    val choices = listOf("All", "Owned", "Joined")
    val scope = rememberCoroutineScope()

    val companyTabsPagerState = rememberPagerState(pageCount = { tabs.size }, initialPage = 0)
    val projectPagerState = rememberPagerState(pageCount = { choices.size })

    var isShowEditDialog by remember { mutableStateOf(false) }
    var isShowCreateProject by remember { mutableStateOf(false) }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }

    val isOwner by remember(state.userId, state.tenant) {
        mutableStateOf(state.userId != null && state.userId == state.tenant?.owner?.id)
    }

    val projectsToShow by remember(state.projects, projectPagerState.currentPage, state.userId) {
        derivedStateOf {
            when (projectPagerState.currentPage) {
                0 -> state.projects
                1 -> state.projects.filter { it.creatorId == state.userId }
                2 -> state.projects.filterNot { it.creatorId == state.userId }
                else -> emptyList()
            }
        }
    }

    ScreenContainer(
        title = state.tenant?.name ?: "Loading...",
        isPinned = state.isPinned,
        isOwner = isOwner,
        onPinClick = onPinClick,
        onDeleteClick = { showDeleteConfirmationDialog = true },
        onEditClick = { isShowEditDialog = true },
        onBackClick = onBackClick,
        modifier = modifier,
        isLoading = state.isLoading && state.tenant == null,
        image = state.tenant?.image,
        hasImageBackground = true,
        onCreateClick = { isShowCreateProject = true }
    ) { paddingValues, scrollBehavior ->
        LazyColumn(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            stickyHeader {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 2.dp
                ) {
                    CustomTabRow(
                        tabs = tabs,
                        selectedTabIndex = companyTabsPagerState.currentPage,
                        onTabClick = { index ->
                            scope.launch { companyTabsPagerState.animateScrollToPage(index) }
                        }
                    )
                }
            }

            item {
                HorizontalPager(
                    state = companyTabsPagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 400.dp)
                ) { page ->
                    when (page) {
                        0 -> CompanyInfoPage(
                            state,
                            isOwner,
                            Modifier.fillMaxSize(),
                            onRemoveMemberClick
                        )

                        1 -> {
                            Column(
                                Modifier
                                    .fillMaxSize()
                            ) {
                                SingleSelectChipRow(
                                    choices = choices,
                                    initialSelectedIndex = projectPagerState.currentPage,
                                    onChoiceSelected = { index ->
                                        scope.launch { projectPagerState.animateScrollToPage(index) }
                                    }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                HorizontalPager(
                                    state = projectPagerState,
                                    userScrollEnabled = false
                                ) { projectPageIdx ->
                                    ProjectList(
                                        projects = projectsToShow,
                                        pinnedProjects = state.pinnedProjects,
                                        scrollBehavior = scrollBehavior,
                                        onProjectClick = onProjectClick,
                                        onProjectSwipeToDelete = onProjectSwipeToDelete,
                                        onProjectSwipeToPin = onProjectSwipeToPin
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = isShowEditDialog,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300))
    ) {
        CompanyDialog(
            onDismiss = { isShowEditDialog = false },
            onSubmit = { updatedCompanyUiModel ->
                onEditConfirm(updatedCompanyUiModel)
                isShowEditDialog = false
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

    if (isShowCreateProject) {
        ProjectDialog(
            onDismiss = { isShowCreateProject = false },
            onSubmit = { projectRequest ->
                onCreateProject(projectRequest)
                isShowCreateProject = false
            },
        )
    }

    if (showDeleteConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            title = {
                Text(
                    text = "Delete Company",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete this company? This action cannot be undone.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteClick()
                    showDeleteConfirmationDialog = false
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmationDialog = false }) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onBackground)
                }
            },
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    }
}

@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun CompanyDetailsContentPreview() {
    val userList = remember { userList }

    var ownedProjects by remember { mutableStateOf(projectList) }

    var joinedProjects by remember { mutableStateOf(projectList) }

    var pinnedProjects by remember { mutableStateOf(listOf<Project>()) }

    val allProjectsForSwipe = remember(ownedProjects, joinedProjects) {
        ownedProjects + joinedProjects
    }

    var uiState by remember {
        mutableStateOf(
            CompanyDetailsUiState(
                isPinned = false,
                isLoading = false,
                tenant = Tenant(
                    name = "Preview Company Name",
                    image = null,
                    tenantCode = "PRE-001",
                    tenantUrl = "https://previewcompany.com",
                    joinedUsers = userList,
                    owner = userList[0],
                    keywords = "Technology,Software,Startup,FinTech",
                    description = "a dynamic and forward-thinking organization dedicated to delivering innovative solutions that empower businesses to achieve their full potential. We specialize in partnering with clients to understand their unique challenges and opportunities, leveraging our expertise in [mention a general area",
                    id = 5,
                    ownerID = 1,
                    role = null
                ),
                tenantId = 6,
                projects = projectList,
                userId = 5,
                pinnedProjects = pinnedProjects
            )
        )
    }

    DevDashTheme {
        CompanyDetailsContent(
            state = uiState,
            onRemoveMemberClick = { },
            onDeleteClick = { },
            onPinClick = { uiState = uiState.copy(isPinned = !uiState.isPinned) },
            onEditConfirm = { },
            onBackClick = { },
            onCreateProject = { },
            onProjectClick = { id -> println("Project $id clicked") },
            onProjectSwipeToDelete = { id ->
                ownedProjects = ownedProjects.filter { it.id != id }
                joinedProjects = joinedProjects.filter { it.id != id }
                pinnedProjects = pinnedProjects.filter { it.id != id }
                true
            },
            onProjectSwipeToPin = { id ->
                val projectToPin = allProjectsForSwipe.find { it.id == id }
                if (projectToPin != null) {
                    pinnedProjects = if (pinnedProjects.contains(projectToPin)) {
                        pinnedProjects - projectToPin
                    } else {
                        pinnedProjects + projectToPin
                    }
                }
                false
            },
        )
    }
}
