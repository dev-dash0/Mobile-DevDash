package com.elfeky.devdash.ui.screens.main_screens.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.elfeky.devdash.ui.base.BaseViewModel
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.pin.PinnedItems
import com.elfeky.domain.usecase.account.GetUserProfileUseCase
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
    private val getProjectByIdUseCase: GetProjectByIdUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : BaseViewModel<HomeReducer.HomeState, HomeReducer.Event, HomeReducer.Effect>(
    initialState = HomeReducer.HomeState(
        isLoadingUser = false,
        isLoadingPinnedItems = false,
        isLoadingUrgentIssues = false
    ),
    reducer = HomeReducer()
) {

    init {
        loadUserProfile()
        loadPinnedItems()
        loadUrgentIssues()
        loadProjects()
    }

    private fun loadUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            sendEvent(HomeReducer.Event.LoadUser)
            getUserProfileUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d("HomeViewModel", "User Profile: " + resource.data.toString())
                        sendEvent(
                            HomeReducer.Event.UserLoaded(
                                resource.data!!
                            )
                        )
                    }

                    is Resource.Error -> sendEvent(
                        HomeReducer.Event.UserLoadingError(
                            resource.message ?: "Unknown error loading user profile"
                        )
                    )

                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun loadPinnedItems() {
        viewModelScope.launch(Dispatchers.IO) {
            sendEvent(HomeReducer.Event.LoadPinnedItems)
            getAllPinnedItemsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d(
                            "HomeViewModel",
                            "Pinned Projects: " + resource.data?.projects.toString()
                        )
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

                    is Resource.Error -> sendEvent(
                        HomeReducer.Event.PinnedItemsLoadingError(
                            resource.message ?: "Unknown error loading pinned items"
                        )
                    )

                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun loadUrgentIssues() {
        viewModelScope.launch(Dispatchers.IO) {
            sendEvent(HomeReducer.Event.LoadUrgentIssues)
            getUserIssuesUseCase().collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val urgentIssues = resource.data
                            ?.filter {
                                val isCompleted = it.status == Status.Completed.text
                                val isCancelled = it.status == Status.Canceled.text
                                val deadlineDateTime = it.deadline?.let { deadline ->
                                    LocalDateTime.parse(
                                        deadline,
                                        DateTimeFormatter.ISO_LOCAL_DATE_TIME
                                    )
                                }
                                val isOverdue =
                                    deadlineDateTime?.isBefore(LocalDateTime.now()) == true
                                val isDueSoon =
                                    deadlineDateTime?.isBefore(
                                        LocalDateTime.now().plus(3, ChronoUnit.DAYS)
                                    ) == true
                                val isHighPriority =
                                    it.priority == Priority.Urgent.text || it.priority == Priority.Critical.text
                                !isCompleted && !isCancelled && (isOverdue || (isDueSoon && isHighPriority))
                            }
                            ?.sortedWith(compareByDescending<Issue> { it.priority.toPriority().ordinal }.thenBy { it.deadline })
                            ?.take(5) ?: emptyList()

                        sendEvent(HomeReducer.Event.UrgentIssuesLoaded(urgentIssues))
                    }

                    is Resource.Error -> sendEvent(
                        HomeReducer.Event.UrgentIssuesLoadingError(
                            resource.message ?: "Unknown error loading urgent issues"
                        )
                    )

                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun loadProjects() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserProjectsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> sendEvent(
                        HomeReducer.Event.ProjectsLoaded(
                            resource.data ?: emptyList()
                        )
                    )

                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }

    fun refreshData() {
        loadUserProfile()
        loadPinnedItems()
        loadUrgentIssues()
        loadProjects()
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