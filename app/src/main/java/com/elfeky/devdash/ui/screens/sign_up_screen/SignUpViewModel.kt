package com.elfeky.devdash.ui.screens.sign_up_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.model.User
import com.elfeky.domain.usecase.RegisterUserUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    var state = mutableStateOf(SignUpState())
        private set

    fun signup(user: User) {
        registerUserUseCase(user).onEach { result ->
            when(result){
                is Resource.Loading ->{
                    state.value = SignUpState(isLoading = true)
                }
                is Resource.Success ->{
                    state.value = SignUpState(signedUp = true)
                }
                is Resource.Error ->{
                    state.value = SignUpState(error = result.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun validatePassword(password: String): Boolean {
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar
    }

    fun validateEmail(email: String): Boolean {
        return email.contains("@") and (email.length >= 10)
    }
}