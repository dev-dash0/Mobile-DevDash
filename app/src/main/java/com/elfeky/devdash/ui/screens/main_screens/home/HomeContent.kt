package com.elfeky.devdash.ui.screens.main_screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.card.CompactIssueCard
import com.elfeky.devdash.ui.common.card.CompanyCard
import com.elfeky.devdash.ui.common.card.ProjectCard
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.common.pinnedItems
import com.elfeky.devdash.ui.common.projectList
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.screens.main_screens.home.HomeReducer.HomeState
import com.elfeky.devdash.ui.screens.main_screens.home.components.Header
import com.elfeky.devdash.ui.screens.main_screens.home.components.PinnedIssueCard
import com.elfeky.devdash.ui.screens.main_screens.home.components.PinnedSprintCard
import com.elfeky.devdash.ui.screens.main_screens.home.components.SectionHeader
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.issue.Issue
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeState,
    navigateToCompany: (id: Int) -> Unit,
    navigateToProject: (id: Int) -> Unit,
    navigateToSprint: (id: Int) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 4 })
    val scope = rememberCoroutineScope()
    val tabTitles = listOf("Company", "Project", "Sprint", "Issue")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Header(state.user?.firstName ?: "", modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            SectionHeader(
                title = "Urgent Issues",
                icon = R.drawable.alert_ic,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        if (!state.urgentIssues.isNullOrEmpty()) {
            items(state.urgentIssues) { issue ->
                CompactIssueCard(
                    issue,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .clickable {
                            if (issue.isBacklog) navigateToProject(issue.projectId)
                            else navigateToSprint(issue.sprintId!!)
                        }
                )
            }
        } else {
            item {
                Text(
                    "No urgent issue",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                )
            }
        }

        item {
            SectionHeader(
                title = "Pins",
                icon = R.drawable.ic_pin,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.padding(horizontal = 12.dp),
                containerColor = Color.Transparent,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    if (pagerState.currentPage < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                },
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                        text = { Text(title) }
                    )
                }
            }
        }

        item {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillParentMaxSize()
            ) { page ->
                LazyColumn(
                    modifier = Modifier.fillParentMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    when (page) {
                        0 -> items(
                            state.pinnedItems?.tenants ?: emptyList(),
                            key = { it.tenantCode }
                        ) { tenant ->
                            CompanyCard(
                                tenant,
                                navigateToCompany,
                                modifier = Modifier.heightIn(max = 150.dp)
                            )
                        }

                        1 -> items(
                            state.pinnedItems?.projects ?: emptyList(),
                            key = { it.projectCode }
                        ) { project ->
                            ProjectCard(
                                project,
                                {
                                    Log.d("Home Content", "${project.name} : ${project.id}")
                                    navigateToProject(project.id)
                                }, {})
                        }

                        2 -> items(
                            state.pinnedItems?.sprints ?: emptyList(),
                            key = { it.id.toString() + it.title }
                        ) { sprint ->
                            PinnedSprintCard(
                                state.projects?.find { it.id == sprint.projectId }?.name
                                    ?: "", sprint,
                                modifier = Modifier.clickable { navigateToSprint(sprint.id) }
                            )
                        }

                        3 -> items(
                            state.pinnedItems?.issues ?: emptyList(),
                            key = { it.id.toString() + it.title }
                        ) { issue ->
                            PinnedIssueCard(
                                state.projects?.find { it.id == issue.projectId }?.name
                                    ?: "",
                                issue,
                                modifier = Modifier.clickable {
                                    if (issue.isBacklog) navigateToProject(issue.projectId)
                                    else navigateToSprint(issue.sprintId!!)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    val urgentIssues = issueList.filter {
        val isCompleted = it.status == Status.Completed.text
        val isCancelled = it.status == Status.Canceled.text
        val deadlineDateTime = it.deadline?.let { deadline ->
            LocalDateTime.parse(deadline, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
        val isOverdue = deadlineDateTime?.isBefore(LocalDateTime.now()) == true
        val isDueSoon =
            deadlineDateTime?.isBefore(LocalDateTime.now().plus(3, ChronoUnit.DAYS)) == true
        val isHighPriority =
            it.priority == Priority.Urgent.text || it.priority == Priority.Critical.text
        !isCompleted && !isCancelled && (isOverdue || (isDueSoon && isHighPriority))
    }
        .sortedWith(compareByDescending<Issue> { it.priority.toPriority().ordinal }.thenBy { it.deadline })
        .take(5)

    DevDashTheme {
        HomeContent(
            state = HomeState(
                user = userList[0],
                urgentIssues = urgentIssues,
                pinnedItems = pinnedItems,
                projects = projectList
            ),
            navigateToCompany = {},
            navigateToProject = {},
            navigateToSprint = {}
        )
    }
}