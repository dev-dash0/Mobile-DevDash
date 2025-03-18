package com.elfeky.devdash.ui.screens.main_screens.more

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.model.account.ChangePasswordRequest
import com.elfeky.domain.model.account.UserProfileRequest
import com.elfeky.domain.usecase.account.ChangePasswordUseCase
import com.elfeky.domain.usecase.account.DeleteAccountUseCase
import com.elfeky.domain.usecase.account.GetUserProfileUseCase
import com.elfeky.domain.usecase.account.LogoutUseCase
import com.elfeky.domain.usecase.account.UpdateProfileUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    var state = mutableStateOf(MoreScreenState())
        private set

    fun logout() {
        logoutUseCase().onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    state.value = MoreScreenState(isLoadingLogout = true)
                }

                is Resource.Success -> {
                    state.value = MoreScreenState(isLoggedOut = true)
                }

                is Resource.Error -> {
                    state.value =
                        MoreScreenState(
                            logoutError = result.message ?: "An unexpected error is occurred "
                        )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getUserProfile() {
        getUserProfileUseCase().onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    state.value = MoreScreenState(isProfileLoading = true)
                }

                is Resource.Success -> {
                    state.value =
                        MoreScreenState(userProfile = result.data, isProfileLoading = false)
                    Log.i("userProfile", result.data.toString())
                }

                is Resource.Error -> {
                    state.value =
                        MoreScreenState(
                            profileError = result.message ?: "An unexpected error is occurred ",
                            isProfileLoading = false
                        )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun deleteAccount() {
        deleteAccountUseCase().onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    state.value = MoreScreenState(isLoadingDelete = true)
                }

                is Resource.Success -> {
                    state.value = MoreScreenState(isAccountDeleted = true)
                }

                is Resource.Error -> {
                    state.value =
                        MoreScreenState(
                            deleteAccountError = result.message
                                ?: "An unexpected error is occurred ",
                        )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun changePassword(changePasswordRequest: ChangePasswordRequest) {
        changePasswordUseCase(changePasswordRequest).onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    state.value = MoreScreenState(isLoadingChangePassword = true)
                }

                is Resource.Success -> {
                    state.value = MoreScreenState(passwordChanged = true)
                }

                is Resource.Error -> {
                    state.value =
                        MoreScreenState(
                            changePasswordError = result.message
                                ?: "An unexpected error is occurred ",
                        )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateProfile(userProfileRequest: UserProfileRequest) {
        updateProfileUseCase(userProfileRequest).onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    state.value = MoreScreenState(isUpdatingProfile = true)
                }

                is Resource.Success -> {
                    state.value = MoreScreenState(profileUpdated = true)
                    this.getUserProfile()
                }

                is Resource.Error -> {
                    state.value =
                        MoreScreenState(
                            updateProfileError = result.message
                                ?: "An unexpected error is occurred ",
                        )
                }
            }
        }.launchIn(viewModelScope)
    }

}