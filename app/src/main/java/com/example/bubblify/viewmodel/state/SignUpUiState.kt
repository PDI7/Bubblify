package com.example.bubblify.viewmodel.state

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccess: String? = null,
    val isError: String? = null
)