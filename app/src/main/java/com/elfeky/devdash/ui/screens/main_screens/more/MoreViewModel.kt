package com.elfeky.devdash.ui.screens.main_screens.more

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.model.ChangePasswordRequest
import com.elfeky.domain.model.LoginResponse
import com.elfeky.domain.usecase.ChangePasswordUseCase
import com.elfeky.domain.usecase.DeleteAccountUseCase
import com.elfeky.domain.usecase.GetUserProfileUseCase
import com.elfeky.domain.usecase.LogoutUseCase
import com.elfeky.domain.util.Constants.ACCESS_TOKEN_KEY
import com.elfeky.domain.util.Constants.REFRESH_TOKEN_KEY
import com.elfeky.domain.util.Constants.USER_DATA_FILE
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    var state = mutableStateOf(MoreScreenState())
        private set

    fun logout(loginResponse: LoginResponse) {
        logoutUseCase(loginResponse).onEach { result ->
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

    fun getUserProfile(accessToken: String) {
        getUserProfileUseCase(accessToken).onEach { result ->
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

    fun deleteAccount(accessToken: String) {
        deleteAccountUseCase(accessToken).onEach { result ->
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

    fun changePassword(accessToken: String, changePasswordRequest: ChangePasswordRequest) {
        changePasswordUseCase(accessToken, changePasswordRequest).onEach { result ->
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

    fun eraseLoginResponse() {
        val editor = context.getSharedPreferences(
            USER_DATA_FILE,
            Context.MODE_PRIVATE
        ).edit()
        editor.putString(ACCESS_TOKEN_KEY, "")
        editor.putString(REFRESH_TOKEN_KEY, "")
        editor.apply()
    }

}