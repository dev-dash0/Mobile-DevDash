package com.elfeky.devdash.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.domain.usecase.account.LoginUserUseCase
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.usecase.local_storage.IsFirstLoginUseCase
import com.elfeky.domain.usecase.local_storage.LoginDataUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    isFirstLoginUseCase: IsFirstLoginUseCase,
    loginDataUseCase: LoginDataUseCase,
    private val accessTokenUseCase: AccessTokenUseCase,
    private val loginUseCase: LoginUserUseCase,
) : ViewModel() {
    private val isFirstLogin = isFirstLoginUseCase.get()
    private val _uiState = MutableStateFlow<AuthState>(AuthState.Loading)
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    init {
        if (!isFirstLogin)
            viewModelScope.launch {
                loginUseCase(loginDataUseCase.get()).collect {
                    _uiState.value = when (it) {
                        is Resource.Loading -> AuthState.Loading
                        is Resource.Success -> {
                            accessTokenUseCase.save(it.data?.accessToken ?: "")
                            AuthState.Success(it.data!!.accessToken)
                        }
                        is Resource.Error -> AuthState.Unauthorized
                    }
                }
            }
        else _uiState.value = AuthState.Unauthorized
    }

    fun getStartDestination(): String =
        if (isFirstLogin) AppScreen.AuthScreens.route
        else AppScreen.MainScreen.route
}