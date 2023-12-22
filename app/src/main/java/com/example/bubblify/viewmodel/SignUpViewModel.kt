package com.example.bubblify.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bubblify.common.isValidEmail
import com.example.bubblify.common.isValidPassword
import com.example.bubblify.common.passwordMatches
import com.example.bubblify.model.Resource
import com.example.bubblify.model.User
import com.example.bubblify.service.AccountService
import com.example.bubblify.service.StorageService
import com.example.bubblify.viewmodel.state.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
@Inject
constructor(
    private val application: Application,
    private val accountService: AccountService,
    private val storageService: StorageService
) : AndroidViewModel(application = application) {

    private var _registerState = MutableStateFlow(value = SignUpUiState())
    val registerState: StateFlow<SignUpUiState> = _registerState.asStateFlow()

    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email

    private val username
        get() = uiState.value.username

    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onUsernameChange(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        uiState.value = uiState.value.copy(emailValidation = "")
        uiState.value = uiState.value.copy(passwordValidation = "")
        uiState.value = uiState.value.copy(repeatPasswordValidation = "")


        if (!email.isValidEmail()) {
            uiState.value = uiState.value.copy(emailValidation = "The email has an Invalid format.")
            return
        }

        if (!password.isValidPassword()) {
            uiState.value =
                uiState.value.copy(passwordValidation =
                "The password must have at least 6 characters and contain a number, small and big letter."
                )
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            uiState.value = uiState.value.copy(repeatPasswordValidation = "The passwords don't match!")
            return
        }

        registerUser(openAndPopUp = openAndPopUp)

    }


    private fun registerUser(
        openAndPopUp: (String, String) -> Unit
    ) = viewModelScope.launch {
        accountService.registerUser(email = email, password = password).collectLatest { result ->
            when (result) {
                is Resource.Loading -> {
                    uiState.value = uiState.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    uiState.value = uiState.value.copy(isLoading = false)
                    uiState.value = uiState.value.copy(isSuccess = true)
                    linkAccount(openAndPopUp = openAndPopUp)
                }

                is Resource.Error -> {
                    uiState.value = uiState.value.copy(isLoading = false)
                    uiState.value = uiState.value.copy(error = result.message)
                }
            }
        }
    }

    private fun linkAccount(openAndPopUp: (String, String) -> Unit) =
        viewModelScope.launch {
            val user = User(
                uuid = accountService.currentUserId,
                username = username,
                email = email,
            )

            storageService.createUser(user)
            Toast.makeText(application, "Successfully registered!", Toast.LENGTH_SHORT).show()
            openAndPopUp("login", "signup")
        }
}