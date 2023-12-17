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
import androidx.lifecycle.ViewModel
import com.example.bubblify.ui.theme.BubblifyTheme
import com.example.bubblify.view.GroupPage
import com.example.bubblify.view.UserPage
import com.example.bubblify.viewmodel.GroupViewModel
import com.example.bubblify.view.LoginPage
import com.example.bubblify.viewmodel.LoginViewModel
import com.example.bubblify.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()
    private val groupViewModel: GroupViewModel = GroupViewModel()
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroupPage(groupViewModel)
            UserPage(viewModel)
            LoginPage(loginViewModel)
        }
    }
}
