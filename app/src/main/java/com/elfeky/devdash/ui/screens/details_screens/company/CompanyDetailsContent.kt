package com.elfeky.devdash.ui.screens.details_screens.company

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
fun CompanyDetailsContent(
    state: CompanyDetailsUiState,
    onRemoveMemberClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onPinClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditConfirm: (state: CompanyDetailsUiState) -> Unit,
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
    val isOwner by remember(
        state.userId,
        state.tenant
    ) { mutableStateOf(state.userId == state.tenant?.owner?.id) }

    val projectsToShow =
        remember(state.projects, projectPagerState.currentPage) {
            when (projectPagerState.currentPage) {
                0 -> state.projects
                1 -> state.projects.filter { it.creatorId == state.userId }
                2 -> state.projects.filterNot { it.creatorId == state.userId }
                else -> emptyList()
            }
        }

    ScreenContainer(
        title = state.tenant?.name ?: "",
        isPinned = state.isPinned,
        isOwner = isOwner,
        onPinClick = onPinClick,
        onDeleteClick = onDeleteClick,
        onEditClick = { isShowEditDialog = true },
        onBackClick = onBackClick,
        modifier = modifier,
        isLoading = state.isLoading,
        image = state.tenant?.image,
        hasImageBackground = true,
        onCreateClick = { isShowCreateProject = true }
    ) { paddingValues, scrollBehavior ->
        LazyColumn(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            stickyHeader {
                CustomTabRow(
                    tabs = tabs,
                    selectedTabIndex = companyTabsPagerState.currentPage,
                    onTabClick = { index ->
                        scope.launch { companyTabsPagerState.animateScrollToPage(index) }
                    }
                )
            }

            item {
                HorizontalPager(
                    state = companyTabsPagerState,
                    modifier = Modifier
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .fillMaxWidth()
                        .fillParentMaxSize()
                ) { page ->
                    when (page) {
                        0 -> CompanyInfoPage(
                            state,
                            isOwner,
                            Modifier.fillParentMaxSize(),
                            onRemoveMemberClick
                        )

                        1 -> {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .fillParentMaxSize()
                            ) {
                                SingleSelectChipRow(
                                    choices = choices,
                                    initialSelectedIndex = projectPagerState.currentPage,
                                    onChoiceSelected = {
                                        scope.launch {
                                            projectPagerState.animateScrollToPage(
                                                it
                                            )
                                        }
                                    })
                                HorizontalPager(projectPagerState, userScrollEnabled = false) {
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
            onSubmit = {
                onEditConfirm(
                    state.copy(
                        tenant = state.tenant?.copy(
                            name = it.title,
                            tenantUrl = it.websiteUrl,
                            keywords = it.keywords,
                            description = it.description,
                            image = it.logoUri.toString()
                        ),
                    )
                )
                isShowEditDialog = false
            },
            company = state.tenant?.let {
                CompanyUiModel(
                    it.name,
                    it.tenantUrl,
                    it.keywords,
                    it.description,
                    it.image
                )
            }
        )
    }

    AnimatedVisibility(
        visible = isShowCreateProject,
        enter = fadeIn(animationSpec = tween(300)) + scaleIn(),
        exit = fadeOut(animationSpec = tween(300)) + scaleOut()
    ) {
        ProjectDialog(
            onDismiss = { isShowCreateProject = false },
            onSubmit = {
                onCreateProject(it)
                isShowCreateProject = false
            },
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
            onEditConfirm = { uiState = it },
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
