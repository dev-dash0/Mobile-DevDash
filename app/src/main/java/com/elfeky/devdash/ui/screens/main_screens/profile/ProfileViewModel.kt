package com.elfeky.devdash.ui.screens.main_screens.profile

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
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    var state = mutableStateOf(ProfileScreenState())
        private set

    private val unexpectedErrorMessage = "An unexpected error occurred"

    init {
        getUserProfile()
    }

    fun logout() {
        logoutUseCase().onEach { result ->
            state.value = when (result) {
                is Resource.Loading -> state.value.copy(
                    isLoadingLogout = true,
                    logoutError = null,
                    isLoggedOut = false
                )

                is Resource.Success -> state.value.copy(
                    isLoggedOut = true,
                    isLoadingLogout = false,
                    logoutError = null
                )

                is Resource.Error -> state.value.copy(
                    logoutError = result.message ?: unexpectedErrorMessage,
                    isLoadingLogout = false,
                    isLoggedOut = false
                )
            }
        }.launchIn(viewModelScope)
    }

    fun getUserProfile() {
        getUserProfileUseCase().onEach { result ->
            state.value = when (result) {
                is Resource.Loading -> state.value.copy(
                    isProfileLoading = true,
                    profileError = null
                )

                is Resource.Success -> {
                    Log.i("userProfile", result.data.toString())
                    state.value.copy(
                        userProfile = result.data,
                        isProfileLoading = false,
                        profileError = null
                    )
                }

                is Resource.Error -> state.value.copy(
                    profileError = result.message ?: unexpectedErrorMessage,
                    isProfileLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }

    fun deleteAccount() {
        deleteAccountUseCase().onEach { result ->
            state.value = when (result) {
                is Resource.Loading -> state.value.copy(
                    isLoadingDelete = true,
                    deleteAccountError = null,
                    isAccountDeleted = false
                )

                is Resource.Success -> state.value.copy(
                    isAccountDeleted = true,
                    isLoadingDelete = false,
                    deleteAccountError = null
                )

                is Resource.Error -> state.value.copy(
                    deleteAccountError = result.message ?: unexpectedErrorMessage,
                    isLoadingDelete = false,
                    isAccountDeleted = false
                )
            }
        }.launchIn(viewModelScope)
    }

    fun changePassword(current: String, new: String) {
        val changePasswordRequest =
            ChangePasswordRequest(currentPassword = current, newPassword = new)
        changePasswordUseCase(changePasswordRequest).onEach { result ->
            state.value = when (result) {
                is Resource.Loading -> state.value.copy(
                    isLoadingChangePassword = true,
                    changePasswordError = null,
                    passwordChanged = false
                )

                is Resource.Success -> state.value.copy(
                    passwordChanged = true,
                    isLoadingChangePassword = false,
                    changePasswordError = null
                )

                is Resource.Error -> state.value.copy(
                    changePasswordError = result.message ?: unexpectedErrorMessage,
                    isLoadingChangePassword = false,
                    passwordChanged = false
                )
            }
        }.launchIn(viewModelScope)
    }

    fun updateProfile(userProfileRequest: UserProfileRequest) {
        updateProfileUseCase(userProfileRequest).onEach { result ->
            when (result) {
                is Resource.Loading -> Unit
                is Resource.Success -> reloadData()
                is Resource.Error -> {
                    state.value = state.value.copy(
                        updateProfileError = result.message ?: unexpectedErrorMessage
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun reloadData() {
        getUserProfileUseCase().onEach { result ->
            state.value = when (result) {
                is Resource.Loading -> state.value

                is Resource.Success -> {
                    Log.i("userProfileReload", result.data.toString())
                    state.value.copy(userProfile = result.data)
                }

                is Resource.Error -> state.value.copy(
                    profileError = result.message ?: unexpectedErrorMessage,
                    isProfileLoading = false,
                    isReloading = false
                )
            }
        }.launchIn(viewModelScope)
    }
}
