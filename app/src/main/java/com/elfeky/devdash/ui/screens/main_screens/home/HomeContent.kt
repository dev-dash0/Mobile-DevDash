package com.elfeky.devdash.ui.screens.main_screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.elfeky.devdash.ui.common.card.CompanyCard
import com.elfeky.devdash.ui.common.card.ProjectCard
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.common.pinnedItems
import com.elfeky.devdash.ui.common.projectList
import com.elfeky.devdash.ui.screens.main_screens.home.components.Header
import com.elfeky.devdash.ui.screens.main_screens.home.components.PinnedIssueCard
import com.elfeky.devdash.ui.screens.main_screens.home.components.PinnedSprintCard
import com.elfeky.devdash.ui.screens.main_screens.home.components.SectionHeader
import com.elfeky.devdash.ui.screens.main_screens.home.components.UrgentIssueCard
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.pin.PinnedItems
import com.elfeky.domain.model.project.Project
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    urgentIssues: List<Issue>,
    pinnedItems: PinnedItems,
    projects: List<Project>,
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
            Header("User", modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            SectionHeader(
                title = "Urgent Issues",
                icon = R.drawable.alert_ic,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        if (urgentIssues.isNotEmpty()) {
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(urgentIssues) { issue ->
                        UrgentIssueCard(
                            issue,
                            modifier = Modifier
                                .width(200.dp)
                                .clickable {
                                    if (issue.isBacklog) navigateToProject(issue.projectId)
                                    else navigateToSprint(issue.sprintId!!)
                                }
                        )
                    }
                }
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
                        0 -> items(pinnedItems.tenants) { item ->
                            CompanyCard(
                                item,
                                navigateToCompany
                            )
                        }

                        1 -> items(pinnedItems.projects) { item ->
                            ProjectCard(
                                item,
                                { navigateToProject(item.id) }, {})
                        }

                        2 -> items(pinnedItems.sprints) { item ->
                            PinnedSprintCard(
                                projects.find { it.id == item.projectId }?.name ?: "", item,
                                modifier = Modifier.clickable { navigateToSprint(item.id) }
                            )
                        }

                        3 -> items(pinnedItems.issues) { item ->
                            PinnedIssueCard(
                                projects.find { it.id == item.projectId }?.name ?: "",
                                item,
                                modifier = Modifier.clickable {
                                    if (item.isBacklog) navigateToProject(item.projectId)
                                    else navigateToSprint(item.sprintId!!)
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
        val deadlineDateTime = it.deadline?.let { deadline ->
            LocalDateTime.parse(deadline, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
        val isOverdue = deadlineDateTime?.isBefore(LocalDateTime.now()) == true
        val isDueSoon =
            deadlineDateTime?.isBefore(LocalDateTime.now().plus(3, ChronoUnit.DAYS)) == true
        val isHighPriority =
            it.priority == Priority.Urgent.text || it.priority == Priority.Critical.text
        !isCompleted && (isOverdue || (isDueSoon && isHighPriority))
    }
        .sortedWith(compareByDescending<Issue> { it.priority.toPriority().ordinal }.thenBy { it.deadline })
        .take(5)

    DevDashTheme {
        HomeContent(
            urgentIssues = urgentIssues,
            pinnedItems = pinnedItems,
            projects = projectList,
            navigateToCompany = {},
            navigateToProject = {},
            navigateToSprint = {}
        )
    }
}