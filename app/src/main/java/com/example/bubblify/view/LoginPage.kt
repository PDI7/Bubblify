package com.example.bubblify.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.view.common.EmailField
import com.example.bubblify.view.common.PasswordField
import com.example.bubblify.view.common.PrimaryButton
import com.example.bubblify.view.common.SecondaryButton
import com.example.bubblify.view.common.Title
import com.example.bubblify.view.common.basicButton
import com.example.bubblify.view.common.fieldModifier
import com.example.bubblify.view.common.titleModifier
import com.example.bubblify.viewmodel.LoginViewModel

@Composable
fun LoginPage(
    mainState: MainState,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState

    val fieldModifier = Modifier.fieldModifier()

    LaunchedEffect(Unit) {
        // Check if user is already logged in
        if (viewModel.isAlreadyLoggedIn())
            mainState.navigate("home")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(text = "Login", Modifier.titleModifier())

        EmailField(uiState.email, viewModel::onEmailChange, fieldModifier)

        PasswordField(uiState.password, viewModel::onPasswordChange, fieldModifier)

        if (uiState.error != null) Text(text = uiState.error!!, color = Color.Red)

        PrimaryButton(
            text = "Login",
            modifier = Modifier
                .basicButton()
                .then(Modifier.testTag("loginButton"))
        ) {
            viewModel.onLogInClick(openAndPopUp = { route, popUp ->
                mainState.navigateAndPopUp(
                    route,
                    popUp
                )
            })
        }

        SecondaryButton(
            text = "Sign up",
            modifier = Modifier
                .basicButton()
                .then(Modifier.testTag("signUpButton"))
        ) {
            mainState.navigate("signUp")
        }
    }
}
