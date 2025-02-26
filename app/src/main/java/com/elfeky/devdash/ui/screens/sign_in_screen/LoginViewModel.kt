package com.elfeky.devdash.ui.screens.sign_in_screen

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.model.LoginRequest
import com.elfeky.domain.usecase.LoginUserUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUserUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var state = mutableStateOf(LoginState())
        private set

    fun login(email: String, password: String) {

        loginUseCase(LoginRequest(email, password),context).onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    state.value = LoginState(isLoading = true)
                }

                is Resource.Success -> {
                    state.value = LoginState(loginResponse = result.data, loggedIn = true)
                }

                is Resource.Error -> {
                    state.value =
                        LoginState(error = result.message ?: "An unexpected error is occurred ")
                }
            }

        }.launchIn(viewModelScope)

    }

}