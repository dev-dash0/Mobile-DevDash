package com.elfeky.devdash.ui.screens.details_screens.company

import com.elfeky.devdash.ui.base.Reducer
import com.elfeky.devdash.ui.common.dialogs.company.model.CompanyUiModel
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.ProjectRequest
import com.elfeky.domain.model.tenant.Tenant
import javax.annotation.concurrent.Immutable

class CompanyDetailsReducer :
    Reducer<CompanyDetailsReducer.State, CompanyDetailsReducer.Event, CompanyDetailsReducer.Effect> {

    @Immutable
    sealed class Event : Reducer.ViewEvent {
        sealed class Update : Event() {
            data class IsLoading(val isLoading: Boolean) : Update()
            data class Company(val tenant: Tenant) : Update()
            data class PinnedStatus(val isPinned: Boolean) : Update()
            data class UserId(val userId: Int) : Update()
            data class Projects(val projects: List<Project>) : Update()
            data class PinnedProjects(val pinnedProjects: List<Project>) : Update()
            data class ProjectsLoading(val isLoading: Boolean) : Update()
        }

        data object BackClicked : Event()
        data object PinCompanyClicked : Event()
        data class CopyTextClicked(val text: String) : Event()
        data object DismissDialogClicked : Event()
        data class ProjectClicked(val projectId: Int) : Event()
        data class RemoveMemberClicked(val memberId: Int) : Event()
        sealed class CompanyAction : Event() {
            data object DeleteClicked : CompanyAction()
            data object ConfirmDeleteClicked : CompanyAction()
            data object EditClicked : CompanyAction()
            data class ConfirmEditClicked(val companyUiModel: CompanyUiModel) : CompanyAction()
            data object DeletionCompleted : CompanyAction()
            data object UpdateCompleted : CompanyAction()
            data class PinCompleted(val isPinned: Boolean) : CompanyAction()
        }

        sealed class ProjectAction : Event() {
            data object CreateClicked : ProjectAction()
            data class ConfirmCreateClicked(val projectRequest: ProjectRequest) : ProjectAction()
            data class SwipedToDelete(val projectId: Int) : ProjectAction()
            data class ConfirmDeleteClicked(val projectId: Int) : ProjectAction()
            data class SwipedToPin(val projectId: Int) : ProjectAction()
            data class Added(val project: Project) : ProjectAction()
            data object DeletionCompleted : ProjectAction()
            data class PinCompleted(val isPinned: Boolean) : ProjectAction()
        }

        sealed class Error(val message: String) : Event() {
            data object CompanyLoadError : Error("Error loading company")
            data object PinnedTenantsLoadError : Error("Failed to check if company is pinned")
            data object UserProfileLoadError : Error("Failed to load user profile")
            data object ProjectsLoadError : Error("Failed to load projects")
            data object PinnedProjectsLoadError : Error("Failed to load pinned projects")
            data object CompanyDeleteError : Error("Failed to delete company")
            data object CompanyUpdateError : Error("Failed to update company")
            data object CompanyPinError : Error("Failed to pin/unpin company")
            data object ProjectAddError : Error("Failed to add project")
            data object ProjectDeleteError : Error("Failed to delete project")
            data object ProjectPinError : Error("Failed to pin/unpin project")
        }
    }

    @Immutable
    sealed class Effect : Reducer.ViewEffect {
        data object NavigateBack : Effect()
        data class NavigateToProjectDetails(val projectId: Int) : Effect()
        data class ShowSnackbar(val message: String, val isLong: Boolean = false) : Effect()
        data class ShowDialog(val type: DialogType) : Effect()
        data object TriggerReloadAllData : Effect()
        data object TriggerDeleteCompany : Effect()
        data class TriggerUpdateCompany(val companyUiModel: CompanyUiModel) : Effect()
        data object TriggerToggleCompanyPin : Effect()
        data class TriggerAddProject(val projectRequest: ProjectRequest) : Effect()
        data class TriggerDeleteProject(val projectId: Int) : Effect()
        data class TriggerToggleProjectPin(val projectId: Int) : Effect()
    }

    @Immutable
    data class State(
        val tenant: Tenant? = null,
        val projects: List<Project> = emptyList(),
        val pinnedProjects: List<Project> = emptyList(),
        val userId: Int? = null,
        val isLoading: Boolean = false,
        val projectsLoading: Boolean = false,
        val isPinned: Boolean = false
    ) : Reducer.ViewState

    @Immutable
    sealed class DialogType {
        data object EditCompany : DialogType()
        data object CreateProject : DialogType()
        data object DeleteCompanyConfirmation : DialogType()
        data class DeleteProjectConfirmation(val projectId: Int) : DialogType()
    }

    override fun reduce(previousState: State, event: Event): Pair<State, Effect?> {
        return when (event) {
            is Event.Update.IsLoading -> previousState.copy(isLoading = event.isLoading) to null
            is Event.Update.Company -> previousState.copy(tenant = event.tenant) to null
            is Event.Update.PinnedStatus -> previousState.copy(isPinned = event.isPinned) to null
            is Event.Update.UserId -> previousState.copy(userId = event.userId) to null
            is Event.Update.Projects -> previousState.copy(projects = event.projects) to null
            is Event.Update.PinnedProjects -> previousState.copy(pinnedProjects = event.pinnedProjects) to null
            is Event.Update.ProjectsLoading -> previousState.copy(projectsLoading = event.isLoading) to null
            Event.BackClicked -> previousState to Effect.NavigateBack
            Event.PinCompanyClicked -> previousState to Effect.TriggerToggleCompanyPin
            is Event.CopyTextClicked -> previousState to Effect.ShowSnackbar(event.text)
            Event.DismissDialogClicked -> previousState to null
            is Event.ProjectClicked -> previousState to Effect.NavigateToProjectDetails(event.projectId)
            is Event.RemoveMemberClicked -> previousState to Effect.ShowSnackbar("Remove member ${event.memberId} (not implemented yet)")
            Event.CompanyAction.DeleteClicked -> previousState to Effect.ShowDialog(DialogType.DeleteCompanyConfirmation)
            Event.CompanyAction.ConfirmDeleteClicked -> previousState to Effect.TriggerDeleteCompany
            Event.CompanyAction.EditClicked -> previousState to Effect.ShowDialog(DialogType.EditCompany)
            is Event.CompanyAction.ConfirmEditClicked -> previousState to Effect.TriggerUpdateCompany(
                event.companyUiModel
            )

            Event.CompanyAction.DeletionCompleted -> previousState to Effect.NavigateBack
            Event.CompanyAction.UpdateCompleted -> previousState to Effect.TriggerReloadAllData
            is Event.CompanyAction.PinCompleted -> previousState to Effect.ShowSnackbar(if (event.isPinned) "Pinned Successfully" else "unPinned Successfully")

            // --- Project Specific Actions ---
            Event.ProjectAction.CreateClicked -> previousState to Effect.ShowDialog(DialogType.CreateProject)
            is Event.ProjectAction.ConfirmCreateClicked -> previousState to Effect.TriggerAddProject(
                event.projectRequest
            )

            is Event.ProjectAction.SwipedToDelete -> previousState to Effect.ShowDialog(
                DialogType.DeleteProjectConfirmation(event.projectId)
            )

            is Event.ProjectAction.ConfirmDeleteClicked -> previousState to Effect.TriggerDeleteProject(
                event.projectId
            )

            is Event.ProjectAction.SwipedToPin -> previousState to Effect.TriggerToggleProjectPin(
                event.projectId
            )

            is Event.ProjectAction.Added -> previousState.copy(projects = previousState.projects + event.project) to Effect.ShowSnackbar(
                "Project added"
            )

            Event.ProjectAction.DeletionCompleted -> previousState to Effect.TriggerReloadAllData
            is Event.ProjectAction.PinCompleted -> previousState to Effect.ShowSnackbar(if (event.isPinned) "Pinned Successfully" else "unPinned Successfully")
            is Event.Error -> previousState to Effect.ShowSnackbar(event.message)
        }
    }

    companion object {
        val initialState = State()
    }
}