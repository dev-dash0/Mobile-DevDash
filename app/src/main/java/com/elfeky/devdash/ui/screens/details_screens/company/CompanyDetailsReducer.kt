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
        data class UpdateIsLoading(val isLoading: Boolean) : Event()
        data class UpdateCompany(val tenant: Tenant) : Event()
        data class UpdatePinnedStatus(val isPinned: Boolean) : Event()
        data class UpdateUserId(val userId: Int) : Event()
        data class UpdateProjects(val projects: List<Project>) : Event()
        data class UpdatePinnedProjects(val pinnedProjects: List<Project>) : Event()

        data object BackClicked : Event()
        data object PinCompanyClicked : Event()
        data class CopyTextClicked(val text: String) : Event()
        data object DeleteCompanyClicked : Event()
        data object ConfirmDeleteCompanyClicked : Event()
        data object EditCompanyClicked : Event()
        data class ConfirmEditCompanyClicked(val companyUiModel: CompanyUiModel) : Event()
        data object CreateProjectClicked : Event()
        data class ConfirmCreateProjectClicked(val projectRequest: ProjectRequest) : Event()
        data class ProjectClicked(val projectId: Int) : Event()
        data class ProjectSwipedToDelete(val projectId: Int) : Event()
        data class ConfirmDeleteProjectClicked(val projectId: Int) : Event()
        data class ProjectSwipedToPin(val projectId: Int) : Event()
        data class RemoveMemberClicked(val memberId: Int) : Event()
        data object DismissDialogClicked : Event()

        data class ProjectAdded(val project: Project) : Event()

        data object CompanyDeletionCompleted : Event()
        data object ProjectDeletionCompleted : Event()
        data object CompanyUpdateCompleted : Event()
        data class PinCompleted(val isPinned: Boolean) : Event()
        data class SetProjectsLoading(val isLoading: Boolean) : Event()

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
            is Event.UpdateIsLoading -> previousState.copy(isLoading = event.isLoading) to null
            Event.BackClicked -> previousState to Effect.NavigateBack
            Event.PinCompanyClicked -> previousState to Effect.TriggerToggleCompanyPin
            is Event.CopyTextClicked -> previousState to Effect.ShowSnackbar(event.text)
            Event.DeleteCompanyClicked -> previousState to Effect.ShowDialog(DialogType.DeleteCompanyConfirmation)
            Event.ConfirmDeleteCompanyClicked -> previousState to Effect.TriggerDeleteCompany

            Event.EditCompanyClicked -> previousState to Effect.ShowDialog(DialogType.EditCompany)
            is Event.ConfirmEditCompanyClicked -> previousState to Effect.TriggerUpdateCompany(event.companyUiModel)

            Event.CreateProjectClicked -> previousState to Effect.ShowDialog(DialogType.CreateProject)
            is Event.ConfirmCreateProjectClicked -> previousState to Effect.TriggerAddProject(
                event.projectRequest
            )

            is Event.ProjectClicked -> previousState to Effect.NavigateToProjectDetails(event.projectId)
            is Event.ProjectSwipedToDelete -> previousState to Effect.ShowDialog(
                DialogType.DeleteProjectConfirmation(
                    event.projectId
                )
            )

            is Event.ConfirmDeleteProjectClicked -> previousState to Effect.TriggerDeleteProject(
                event.projectId
            )

            is Event.ProjectSwipedToPin -> previousState to Effect.TriggerToggleProjectPin(event.projectId)
            is Event.RemoveMemberClicked -> previousState to Effect.ShowSnackbar("Remove member ${event.memberId} (not implemented yet)")
            Event.DismissDialogClicked -> previousState to null

            is Event.UpdateCompany -> previousState.copy(
                tenant = event.tenant
            ) to null

            is Event.UpdatePinnedStatus -> previousState.copy(isPinned = event.isPinned) to null
            is Event.UpdateUserId -> previousState.copy(userId = event.userId) to null
            is Event.UpdateProjects -> previousState.copy(
                projects = event.projects
            ) to null

            is Event.UpdatePinnedProjects -> previousState.copy(pinnedProjects = event.pinnedProjects) to null
            is Event.ProjectAdded -> previousState.copy(projects = previousState.projects + event.project) to Effect.ShowSnackbar(
                "Project added"
            )

            Event.CompanyDeletionCompleted -> previousState to Effect.NavigateBack
            Event.ProjectDeletionCompleted -> previousState to Effect.TriggerReloadAllData
            Event.CompanyUpdateCompleted -> previousState to Effect.TriggerReloadAllData
            is Event.PinCompleted -> previousState to Effect.ShowSnackbar(if (event.isPinned) "Pinned Successfully" else "unPinned Successfully")

            is Event.Error -> previousState to Effect.ShowSnackbar(event.message)

            is Event.SetProjectsLoading -> previousState.copy(projectsLoading = event.isLoading) to null
        }
    }

    companion object {
        fun initialState() = State()
    }
}