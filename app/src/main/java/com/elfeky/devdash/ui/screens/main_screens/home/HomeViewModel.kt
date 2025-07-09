package com.elfeky.devdash.ui.screens.main_screens.home

import androidx.lifecycle.viewModelScope
import com.elfeky.devdash.ui.base.BaseViewModel
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.pin.PinnedItems
import com.elfeky.domain.usecase.dashboard.GetUserIssuesUseCase
import com.elfeky.domain.usecase.dashboard.GetUserProjectsUseCase
import com.elfeky.domain.usecase.pin.get.GetAllPinnedItemsUseCase
import com.elfeky.domain.usecase.project.GetProjectByIdUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPinnedItemsUseCase: GetAllPinnedItemsUseCase,
    private val getUserIssuesUseCase: GetUserIssuesUseCase,
    private val getUserProjectsUseCase: GetUserProjectsUseCase,
    private val getProjectByIdUseCase: GetProjectByIdUseCase
) : BaseViewModel<HomeReducer.State, HomeReducer.Event, HomeReducer.Effect>(
    initialState = HomeReducer.State(
        isLoadingPinnedItems = false,
        isLoadingUrgentIssues = false
    ),
    reducer = HomeReducer()
) {

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            sendEvent(HomeReducer.Event.LoadPinnedItems)
            getAllPinnedItemsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        sendEvent(
                            HomeReducer.Event.PinnedItemsLoaded(
                                resource.data ?: PinnedItems(
                                    emptyList(),
                                    emptyList(),
                                    emptyList(),
                                    emptyList()
                                )
                            )
                        )
                    }

                    is Resource.Error -> {
                        sendEvent(
                            HomeReducer.Event.PinnedItemsLoadingError(
                                resource.message ?: "Unknown error loading pinned items"
                            )
                        )
                    }

                    is Resource.Loading -> {}
                }
            }
        }

        viewModelScope.launch {
            sendEvent(HomeReducer.Event.LoadUrgentIssues)
            getUserIssuesUseCase().collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val urgentIssues = resource.data?.filter {
                            val isCancelled = it.status == Status.Canceled.text
                            val isCompleted = it.status == Status.Completed.text
                            val deadlineDateTime = it.deadline?.let { deadline ->
                                LocalDateTime.parse(deadline, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                            }
                            val isOverdue = deadlineDateTime?.isBefore(LocalDateTime.now()) == true
                            val isDueSoon =
                                deadlineDateTime?.isBefore(
                                    LocalDateTime.now().plus(3, ChronoUnit.DAYS)
                                ) == true
                            val isHighPriority =
                                it.priority == Priority.Urgent.text || it.priority == Priority.Critical.text
                            !isCancelled && !isCompleted && (isOverdue || (isDueSoon && isHighPriority))
                        }
                            ?.sortedWith(compareByDescending<Issue> { it.priority.toPriority().ordinal }.thenBy { it.deadline })
                            ?.take(5) ?: emptyList()

                        sendEvent(HomeReducer.Event.UrgentIssuesLoaded(urgentIssues))
                    }

                    is Resource.Error -> {
                        sendEvent(
                            HomeReducer.Event.UrgentIssuesLoadingError(
                                resource.message ?: "Unknown error loading urgent issues"
                            )
                        )
                    }

                    is Resource.Loading -> {}
                }
            }
        }

        viewModelScope.launch {
            getUserProjectsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        sendEvent(
                            HomeReducer.Event.ProjectsLoaded(
                                resource.data ?: emptyList()
                            )
                        )
                    }

                    is Resource.Error -> {}

                    is Resource.Loading -> {}
                }
            }
        }
    }

    fun refreshData() {
        loadHomeData()
    }

    fun getRole(id: Int): String {
        lateinit var role: String
        val projectId = state.value.pinnedItems!!.sprints.find { it.id == id }!!.projectId
        val job = viewModelScope.launch(Dispatchers.IO) {
            getProjectByIdUseCase(projectId).collect { result ->
                if (result is Resource.Success) role = result.data?.role ?: ""
            }
        }
        runCatching { kotlinx.coroutines.runBlocking { job.join() } }
        return role
    }
}