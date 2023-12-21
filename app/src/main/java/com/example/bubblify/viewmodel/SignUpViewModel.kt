package com.example.bubblify.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bubblify.viewmodel.state.SignUpUiState
import com.example.bubblify.common.isValidEmail
import com.example.bubblify.common.isValidPassword
import com.example.bubblify.common.passwordMatches
import com.example.bubblify.model.Resource
import com.example.bubblify.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class SignUpViewModel

    @Inject
    constructor(
        application: Application,
        private val accountService: AccountService
    ) : AndroidViewModel(application = application) {


    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    private var _registerState = MutableStateFlow<SignUpUiState>(value = SignUpUiState())
    val registerState: StateFlow<SignUpUiState> = _registerState.asStateFlow()


    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick() {
        System.out.println("onSignUpClick")

        if (!email.isValidEmail()) {
            //SnackbarManager.showMessage("AppText.email_error")
            return
        }

        if (!password.isValidPassword()) {
            //SnackbarManager.showMessage("AppText.password_error")
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            //SnackbarManager.showMessage("AppText.password_match_error")
            return
        }
        //SnackbarManager.showMessage("Todo signup")

        System.out.println("Email: $email")
        registerUser(email = email, password = password)

    }


    private fun registerUser(email: String, password: String) = viewModelScope.launch {
        accountService.registerUser(email = email, password = password).collectLatest { result ->
            when(result) {
                is Resource.Loading -> {
                    _registerState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _registerState.update { it.copy(isSuccess = "Register Successful!") }
                }

                is Resource.Error -> {
                    _registerState.update { it.copy(isError = result.message) }
                }
            }
        }
    }
}