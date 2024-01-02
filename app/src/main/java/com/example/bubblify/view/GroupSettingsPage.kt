package com.example.bubblify.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.GroupSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupSettingsPage(
    mainState: MainState,
    viewModel: GroupSettingsViewModel = hiltViewModel()
) {
    var selectedChoice by remember { mutableStateOf("Members") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { mainState.navigate("bubbleMain") }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "ArrowBack"
                        )
                    }
                },
                title = { Text("Group Settings") },
                actions = {
                    IconButton(onClick = { /* TODO : Color change popup */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Palette,
                            contentDescription = "Palette"
                        )
                    }
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Button(
                        onClick = { selectedChoice = "Members" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(0.dp))
                            .background(Color.Transparent)
                            .height(35.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        ),
                        border = BorderStroke(0.dp, Color.Transparent),
                    ) {
                        Text(
                            text = "Members",
                            color = if (selectedChoice == "Members") Color.Black else Color.LightGray,
                            maxLines = 1
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(if (selectedChoice == "Members") Color.Black else Color.LightGray)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Button(
                        onClick = { selectedChoice = "Activities" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(0.dp))
                            .background(Color.Transparent)
                            .height(35.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(0.dp, Color.Transparent),
                    ) {
                        Text(
                            text = "Activities",
                            color = if (selectedChoice == "Activities") Color.Black else Color.LightGray,
                            //fontSize = 16.sp,
                            maxLines = 1
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(if (selectedChoice == "Activities") Color.Black else Color.LightGray)
                    )
                }
            }

            when (selectedChoice) {
                "Members" -> DisplayMembersContent()
                "Activities" -> DisplayActivitiesContent()
            }
        }
        NavigationBar(navController = mainState.navController)
    }
}

@Composable
fun DisplayMembersContent() {
    // Display members content here
    Text(text = "Members Content")
}

@Composable
fun DisplayActivitiesContent() {
    // Display activities content here
    Text(text = "Activities Content")
}