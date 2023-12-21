package com.example.bubblify.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bubblify.common.EmailField
import com.example.bubblify.common.PasswordField
import com.example.bubblify.common.PrimaryButton
import com.example.bubblify.common.RepeatPasswordField
import com.example.bubblify.common.SecondaryButton
import com.example.bubblify.common.basicButton
import com.example.bubblify.common.fieldModifier
import com.example.bubblify.viewmodel.SignUpViewModel

@Composable
fun SignUpPage(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    val fieldModifier = Modifier.fieldModifier()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Up")

        EmailField(uiState.email, viewModel::onEmailChange, fieldModifier)

        PasswordField(uiState.password, viewModel::onPasswordChange, fieldModifier)

        RepeatPasswordField(
            uiState.repeatPassword,
            viewModel::onRepeatPasswordChange,
            fieldModifier
        )

        PrimaryButton(text = "Create Account", modifier = Modifier.basicButton()) {
            viewModel.onSignUpClick()
        }

        SecondaryButton(text = "Sign In", modifier = Modifier.basicButton()) {
            navController.navigate("login")
        }
    }
}