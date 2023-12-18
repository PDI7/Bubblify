package com.example.bubblify.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bubblify.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(loginViewModel: LoginViewModel, navController: NavController) {

    Box( //this is the screen
        Modifier
            .requiredWidth(width = 360.dp)
            .requiredHeight(height = 640.dp)
            .background(color = Color.White)
    )

    {
        //this is the Log in Button
        OutlinedButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 37.dp,
                    y = 246.dp
                )
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 60.dp)
        ) {
            Text(
                text = "Log in",
                color = Color.White.copy(alpha = 0.87f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    //.align(alignment = Alignment.TopCenter) //TODO: wtf
                    .offset(
                        x = (-1).dp,
                        y = 0.dp
                    )
                    .requiredWidth(width = 199.dp)
                    .requiredHeight(height = 30.dp)
            )
        }

        //this is the signup button
        OutlinedButton(
            onClick = { /*TODO: check for username/password */
                      //navController.navigate("groupview")
                      },
            border = BorderStroke(2.dp, Color.LightGray),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 37.dp,
                    y = 320.dp
                )
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 60.dp)
        ) {
            Text(
                text = "Sign up",
                color = Color.Black.copy(alpha = 0.87f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    //.align(alignment = Alignment.TopCenter) //TODO: wtf
                    .offset(
                        x = 0.dp,
                        y = 0.dp
                    )
                    .requiredWidth(width = 199.dp)
                    .requiredHeight(height = 30.dp)
            )
        }


        var password by remember { mutableStateOf("") } //idk yet
        var username by remember { mutableStateOf("") }

        OutlinedTextField(
            value = "Enter password here",
            //placeholder = {Text("Enter password here")},
            label = {Text("Password")},
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White.copy(alpha = 0.87f)),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 36.dp,
                    y = 157.dp
                )
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 60.dp))

        OutlinedTextField(
            value = "Enter Username here",
            label = {Text("Username")},
            onValueChange = { username = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White.copy(alpha = 0.87f)),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 36.dp,
                    y = 79.dp
                )
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 60.dp))
    }
}

/*
@Preview(widthDp = 360, heightDp = 640)
@Composable
private fun LoginScreenPreview() {
    LoginPage(loginViewModel = LoginViewModel(), NavController())
}*/
