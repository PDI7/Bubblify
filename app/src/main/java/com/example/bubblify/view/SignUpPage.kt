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
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.view.common.BasicField
import com.example.bubblify.view.common.EmailField
import com.example.bubblify.view.common.PasswordField
import com.example.bubblify.view.common.PrimaryButton
import com.example.bubblify.view.common.RepeatPasswordField
import com.example.bubblify.view.common.SecondaryButton
import com.example.bubblify.view.common.basicButton
import com.example.bubblify.view.common.fieldModifier
import com.example.bubblify.viewmodel.SignUpViewModel

@Composable
fun SignUpPage(
    mainState: MainState,
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

        EmailField(
            uiState.email,
            viewModel::onEmailChange,
            fieldModifier,
            errorMessage = uiState.emailValidation
        )

        BasicField(
            "Username",
            uiState.username,
            viewModel::onUsernameChange,
            fieldModifier,
        )

        PasswordField(
            uiState.password,
            viewModel::onPasswordChange,
            fieldModifier,
            uiState.passwordValidation
        )

        RepeatPasswordField(
            uiState.repeatPassword,
            viewModel::onRepeatPasswordChange,
            fieldModifier,
            uiState.repeatPasswordValidation
        )

        if (uiState.error != null) Text(text = uiState.error!!, color = Color.Red)

        PrimaryButton(text = "Create Account", modifier = Modifier.basicButton()) {
            viewModel.onSignUpClick(openAndPopUp = { route, popUp ->
                mainState.navigateAndPopUp(
                    route,
                    popUp
                )
            })
        }

        SecondaryButton(text = "Sign In", modifier = Modifier.basicButton()) {
            mainState.navigate("login")
        }
    }
}
