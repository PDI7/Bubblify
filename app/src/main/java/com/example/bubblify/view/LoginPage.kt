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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bubblify.common.EmailField
import com.example.bubblify.common.PasswordField
import com.example.bubblify.common.PrimaryButton
import com.example.bubblify.common.SecondaryButton
import com.example.bubblify.common.basicButton
import com.example.bubblify.common.fieldModifier
import com.example.bubblify.viewmodel.LoginViewModel

@Composable
fun LoginPage(viewModel: LoginViewModel, navController: NavController) {

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
        Text(text = "Login")

        EmailField(uiState.email, viewModel::onEmailChange, fieldModifier)

        PasswordField(uiState.password, viewModel::onPasswordChange, fieldModifier)

        PrimaryButton(text = "Login", modifier = Modifier.basicButton()) {
            viewModel.onLogInClick()
        }

        SecondaryButton(text = "Sign up", modifier = Modifier.basicButton()) {
            navController.navigate("signUp")
        }
    }
}


@Preview(widthDp = 360, heightDp = 640)
@Composable
private fun LoginScreenPreview() {
    LoginPage(viewModel = LoginViewModel(), navController = rememberNavController())
}
