package com.example.bubblify.view.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicField(
    label: String,
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String = "",
    errorModifier: Modifier = Modifier
) {
    OutlinedTextField(
        label = { Text(label) },
        isError = errorMessage.isNotEmpty(),
        supportingText = {
            if (errorMessage.isNotEmpty()) Text(
                errorMessage,
                modifier = errorModifier
            )
        },
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String = ""
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier.then(Modifier.testTag("emailField")),
        value = value,
        isError = errorMessage.isNotEmpty(),
        supportingText = {
            if (errorMessage.isNotEmpty()) Text(
                errorMessage,
                modifier.then(Modifier.testTag("emailErrorField"))
            )
        },
        label = { Text("Email") },
        onValueChange = { onNewValue(it) },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White.copy(alpha = 0.87f)
        ),
        placeholder = { Text("Enter email here") },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
    )
}

@Composable
fun PasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String = ""
) {
    PasswordField(
        value,
        "Password",
        onNewValue,
        modifier.then(Modifier.testTag("passwordField")),
        errorMessage,
        Modifier.testTag("passwordErrorField")
    )
}

@Composable
fun RepeatPasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String = ""
) {
    PasswordField(
        value,
        "Repeat Password",
        onNewValue,
        modifier.then(Modifier.testTag("repeatPasswordField")),
        errorMessage,
        Modifier.testTag("repeatPasswordErrorField")
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PasswordField(
    value: String,
    label: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String = "",
    errorModifier: Modifier = Modifier
) {
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        label = { Text(label) },
        placeholder = { Text(text = "Enter password here") },
        isError = errorMessage.isNotEmpty(),
        supportingText = {
            if (errorMessage.isNotEmpty()) Text(
                errorMessage,
                modifier = errorModifier
            )
        },
        onValueChange = { onNewValue(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White.copy(alpha = 0.87f)
        ),
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        }
    )
}