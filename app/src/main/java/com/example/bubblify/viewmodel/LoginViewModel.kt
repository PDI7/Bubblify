package com.example.bubblify.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bubblify.model.Resource
import com.example.bubblify.service.AccountService
import com.example.bubblify.viewmodel.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val application: Application,
    private val accountService: AccountService
) : AndroidViewModel(application = application) {

    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onLogInClick(openAndPopUp: (String, String) -> Unit) {
        loginUser(openAndPopUp, email = email, password = password)
    }

    private fun loginUser(
        openAndPopUp: (String, String) -> Unit,
        email: String,
        password: String
    ) = viewModelScope.launch {
        accountService.loginUser(email = email, password = password).collectLatest { result ->
            when (result) {
                is Resource.Loading -> {
                    uiState.value = uiState.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    uiState.value = uiState.value.copy(isLoading = false)
                    uiState.value = uiState.value.copy(isSuccess = true)
                    openAndPopUp("profile", "login")
                }

                is Resource.Error -> {
                    uiState.value = uiState.value.copy(isLoading = false)
                    uiState.value = uiState.value.copy(error = result.message)
                    Toast.makeText(application, "Invalid Login", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}