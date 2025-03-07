package com.elfeky.devdash.ui.screens.auth_screens.sign_up_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.model.account.User
import com.elfeky.domain.usecase.RegisterUserUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    var state = mutableStateOf(SignUpState())
        private set

    fun signup(user: User) {
        registerUserUseCase(user).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    state.value = SignUpState(isLoading = true)
                }

                is Resource.Success -> {
                    state.value = SignUpState(signedUp = true)
                }

                is Resource.Error -> {
                    state.value = SignUpState(error = result.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun validatePassword(password: String): Boolean =
        password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%?&])[A-Za-z\\d@\$!%?&]{8,}$"))

    fun validateEmail(email: String): Boolean =
        email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))

}