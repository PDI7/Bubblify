package com.example.bubblify.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.view.common.SecondaryButton
import com.example.bubblify.view.common.basicButton
import com.example.bubblify.viewmodel.MoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MorePage(
    mainState: MainState,
    moreViewModel: MoreViewModel = hiltViewModel()
) {

    Box( //this is the screen
        Modifier
            .fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = { Text("More", maxLines = 1) },
            navigationIcon = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "get back"
                    )
                }
            }
        )

        var checked by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        ) {

            Column {
                //settings
                Text(
                    text = "Dark mode",
                    color = Color.Black.copy(alpha = 0.87f),
                    style = TextStyle(
                        fontSize = 15.sp
                    )
                )

                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    }
                )
            }

            SecondaryButton(
                text = "Log out", modifier = Modifier
                    .basicButton()
                    .then(Modifier.testTag("logoutButton"))
            ) {
                moreViewModel.logout(mainState)
            }

        }
        NavigationBar(mainState.navController)
    }
}
