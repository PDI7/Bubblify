package com.example.bubblify.viewmodel.state

data class SignUpUiState(
    val email: String = "",
    val emailValidation: String = "",
    val username: String = "",
    val usernameValidation: String = "",
    val password: String = "",
    val passwordValidation: String = "",
    val repeatPassword: String = "",
    val repeatPasswordValidation: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)