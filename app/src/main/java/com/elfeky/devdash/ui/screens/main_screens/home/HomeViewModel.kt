package com.elfeky.devdash.ui.screens.main_screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfeky.domain.usecase.account.LoginUserUseCase
import com.elfeky.domain.usecase.local_storage.LoginDataUseCase
import com.elfeky.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    loginDataUseCase: LoginDataUseCase,
    private val loginUseCase: LoginUserUseCase
) : ViewModel() {
    private val loginData = loginDataUseCase.get()
    var state = mutableStateOf(HomeState())
        private set

    init {
        Log.i("HomeViewModel", loginData.email + '\n' + loginData.password)
        login()
    }

    private fun login() {
        loginUseCase(loginData).onEach { result ->
            when (result) {

                is Resource.Loading -> state.value = HomeState(isLoading = true)


                is Resource.Success -> state.value = HomeState(loggedIn = true, isLoading = false)


                is Resource.Error -> state.value =
                    HomeState(error = result.message ?: "An unexpected error is occurred ")

            }

        }.launchIn(viewModelScope)
    }
}

