package com.example.bubblify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bubblify.ui.theme.BubblifyTheme

import com.example.bubblify.view.UserPage
import com.example.bubblify.view.LoginPage
import com.example.bubblify.view.MorePage
import com.example.bubblify.view.ProfilePage
import com.example.bubblify.view.SetActivityPage

import com.example.bubblify.viewmodel.LoginViewModel
import com.example.bubblify.viewmodel.MoreViewModel
import com.example.bubblify.viewmodel.UserViewModel
import com.example.bubblify.viewmodel.ProfilePageViewModel
import com.example.bubblify.viewmodel.SetActivityViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val moreViewModel: MoreViewModel by viewModels()
    private val profilePageViewModel: ProfilePageViewModel by viewModels()
    private val setActivityViewModel: SetActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserPage(viewModel)
            LoginPage(loginViewModel)
            MorePage(moreViewModel)
            SetActivityPage(setActivityViewModel)
            ProfilePage(profilePageViewModel)
        }
    }
}
