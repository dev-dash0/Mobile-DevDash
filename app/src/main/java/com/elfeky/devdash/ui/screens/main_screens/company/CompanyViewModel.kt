package com.elfeky.devdash.ui.screens.main_screens.company

import androidx.lifecycle.viewModelScope
import com.elfeky.devdash.ui.base.BaseViewModel
import com.elfeky.domain.model.tenant.TenantRequest
import com.elfeky.domain.usecase.join.JoinTenantUseCase
import com.elfeky.domain.usecase.pin.get.GetPinnedTenantsUseCase
import com.elfeky.domain.usecase.pin.pin.PinTenantUseCase
import com.elfeky.domain.usecase.pin.unpin.UnpinTenantUseCase
import com.elfeky.domain.usecase.tenant.AddTenantUseCase
import com.elfeky.domain.usecase.tenant.DeleteTenantUseCase
import com.elfeky.domain.usecase.tenant.GetAllTenantsUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val getAllTenantsUseCase: GetAllTenantsUseCase,
    private val getPinnedTenantUseCase: GetPinnedTenantsUseCase,
    private val addTenantsUseCase: AddTenantUseCase,
    private val deleteTenantUseCase: DeleteTenantUseCase,
    private val pinTenantUseCase: PinTenantUseCase,
    private val unpinTenantUseCase: UnpinTenantUseCase,
    private val joinTenantUseCase: JoinTenantUseCase
) : BaseViewModel<CompanyReducer.State, CompanyReducer.Event, CompanyReducer.Effect>(
    CompanyReducer.State(),
    CompanyReducer()
) {
    init {
        observeEffects()
        loadCompanies()
        loadPinnedCompanies()
    }

    fun onEvent(event: CompanyReducer.Event) {
        when (event) {
            is CompanyReducer.Event.Update -> sendEvent(event)
            else -> sendEventForEffect(event)
        }
    }

    private fun observeEffects() {
        viewModelScope.launch {
            internalEffect.collect { effect ->
                when (effect) {
                    is CompanyReducer.Effect.NavigateToCompanyDetails -> sendUiEffect(effect)

                    is CompanyReducer.Effect.AddCompany -> addTenant(effect.request)
                    is CompanyReducer.Effect.TogglePinStatus -> togglePinStatus(
                        effect.id,
                        effect.isPinned
                    )

                    is CompanyReducer.Effect.DeleteCompany -> deleteTenant(effect.id)
                    CompanyReducer.Effect.RefreshCompanyList -> loadCompanies()
                    is CompanyReducer.Effect.JoinCompany -> joinTenant(effect.code)
                }
            }
        }
    }

    private fun loadCompanies() {
        sendEvent(CompanyReducer.Event.Update.IsLoading(true))
        viewModelScope.launch {
            onEvent(CompanyReducer.Event.Update.Companies(getAllTenantsUseCase()))
        }
    }

    private fun loadPinnedCompanies() {
        viewModelScope.launch {
            getPinnedTenantUseCase().collect { result ->
                if (result is Resource.Success && result.data != null) {
                    onEvent(CompanyReducer.Event.Update.PinnedCompanies(result.data!!))
                }
            }
        }
    }

    private fun addTenant(request: TenantRequest) {
        viewModelScope.launch {
            addTenantsUseCase(request).collect { result ->
                if (result is Resource.Success) {
                    sendUiEffect(CompanyReducer.Effect.RefreshCompanyList) // Use sendUiEffect for refresh
                } else if (result is Resource.Error) {
                    sendEvent(
                        CompanyReducer.Event.Update.Error(
                            result.message ?: "Failed to add company"
                        )
                    )
                }
            }
        }
    }

    private fun deleteTenant(id: Int) {
        viewModelScope.launch {
            deleteTenantUseCase(id).collect { result ->
                if (result is Resource.Success) {
                    sendUiEffect(CompanyReducer.Effect.RefreshCompanyList)
                } else if (result is Resource.Error) {
                    sendEvent(
                        CompanyReducer.Event.Update.Error(
                            result.message ?: "Failed to delete company"
                        )
                    )
                }
            }
        }
    }

    private fun joinTenant(code: String) {
        viewModelScope.launch {
            joinTenantUseCase(code).collect { result ->
                if (result is Resource.Success) {
                    sendUiEffect(CompanyReducer.Effect.RefreshCompanyList)
                } else if (result is Resource.Error) {
                    sendEvent(
                        CompanyReducer.Event.Update.Error(
                            result.message ?: "Failed to join company"
                        )
                    )
                }
            }
        }
    }

    private fun togglePinStatus(id: Int, isCurrentlyPinned: Boolean) {
        viewModelScope.launch {
            if (isCurrentlyPinned) {
                unpinTenantUseCase(id).collect {}
            } else {
                pinTenantUseCase(id).collect {}
            }
            loadPinnedCompanies()
        }
    }
}
