package com.elfeky.devdash.ui.screens.main_screens.company

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.elfeky.devdash.ui.base.Reducer
import com.elfeky.devdash.ui.base.Reducer.ViewEffect
import com.elfeky.devdash.ui.base.Reducer.ViewEvent
import com.elfeky.devdash.ui.base.Reducer.ViewState
import com.elfeky.devdash.ui.screens.main_screens.company.CompanyReducer.Effect.AddCompany
import com.elfeky.devdash.ui.screens.main_screens.company.CompanyReducer.Effect.NavigateToCompanyDetails
import com.elfeky.domain.model.tenant.Tenant
import com.elfeky.domain.model.tenant.TenantRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CompanyReducer :
    Reducer<CompanyReducer.State, CompanyReducer.Event, CompanyReducer.Effect> {

    override fun reduce(
        previousState: State,
        event: Event
    ): Pair<State, Effect?> {
        return when (event) {
            is Event.Update.IsLoading -> {
                previousState.copy(isLoading = true, error = null) to null
            }

            is Event.Update.Companies -> {
                previousState.copy(isLoading = false, companies = event.companies) to null
            }

            is Event.Update.Filter -> {
                previousState.copy(selectedFilterIndex = event.index) to null
            }

            is Event.CompanyClicked -> {
                previousState to NavigateToCompanyDetails(event.companyId)
            }

            is Event.Update.PinnedCompanies -> {
                previousState.copy(pinnedCompanies = event.pinnedCompanies) to null
            }

            is Event.Update.Error -> {
                previousState.copy(isLoading = false, error = event.message) to null
            }

            Event.RefreshCompanies -> {
                previousState to Effect.RefreshCompanyList
            }

            is Event.AddCompany -> previousState to AddCompany(event.request)

            is Event.TogglePinStatus -> previousState to Effect.TogglePinStatus(
                event.id,
                event.isPinned
            )

            is Event.DeleteCompany -> previousState to Effect.DeleteCompany(event.id)
        }
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val companies: Flow<PagingData<Tenant>> = flowOf(PagingData.empty()),
        val pinnedCompanies: List<Tenant> = emptyList(),
        val selectedFilterIndex: Int = 0,
        val error: String? = null
    ) : ViewState

    @Immutable
    sealed class Event : ViewEvent {
        sealed class Update : Event() {
            data class IsLoading(val isLoading: Boolean) : Update()
            data class Companies(val companies: Flow<PagingData<Tenant>>) : Update()
            data class Filter(val index: Int) : Update()
            data class PinnedCompanies(val pinnedCompanies: List<Tenant>) : Update()
            data class Error(val message: String) : Update()
        }

        data class CompanyClicked(val companyId: Int) : Event()
        data class AddCompany(val request: TenantRequest) : Event()
        object RefreshCompanies : Event()
        data class TogglePinStatus(val id: Int, val isPinned: Boolean) : Event()
        data class DeleteCompany(val id: Int) : Event()
    }

    @Immutable
    sealed class Effect : ViewEffect {
        data class NavigateToCompanyDetails(val companyId: Int) : Effect()
        object RefreshCompanyList : Effect()
        data class AddCompany(val request: TenantRequest) : Effect()
        data class TogglePinStatus(val id: Int, val isPinned: Boolean) : Effect()
        data class DeleteCompany(val id: Int) : Effect()
    }
}