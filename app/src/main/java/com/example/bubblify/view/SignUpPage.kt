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
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.view.common.BasicField
import com.example.bubblify.view.common.EmailField
import com.example.bubblify.view.common.PasswordField
import com.example.bubblify.view.common.PrimaryButton
import com.example.bubblify.view.common.RepeatPasswordField
import com.example.bubblify.view.common.SecondaryButton
import com.example.bubblify.view.common.Title
import com.example.bubblify.view.common.basicButton
import com.example.bubblify.view.common.fieldModifier
import com.example.bubblify.view.common.titleModifier
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
        Title(text = "Sign Up", Modifier.titleModifier())

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
            fieldModifier.then(Modifier.testTag("usernameField")),
            uiState.usernameValidation,
            Modifier.testTag("usernameErrorField")
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

        if (uiState.error != null) Text(
            text = uiState.error!!,
            color = Color.Red,
            modifier = Modifier.testTag("errorText")
        )

        PrimaryButton(
            text = "Create Account", modifier = Modifier
                .basicButton()
                .then(Modifier.testTag("createAccountButton"))
        ) {
            viewModel.onSignUpClick(openAndPopUp = { route, popUp ->
                mainState.navigateAndPopUp(
                    route,
                    popUp
                )
            })
        }

        SecondaryButton(
            text = "Sign In", modifier = Modifier
                .basicButton()
                .then(Modifier.testTag("signInButton"))
        ) {
            mainState.navigate("login")
        }
    }
}
