package com.example.bubblify.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.R
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    mainState: MainState,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    //screen
    Box(
        Modifier
            .fillMaxSize()
    ) {

        //top bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Profile",
                    maxLines = 1,
                    modifier = Modifier.testTag("profileTitle")
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { mainState.navigate("home") },
                    modifier = Modifier.testTag("profileBackButton")
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "get back"
                    )
                }
            }
        )

        //profile picture
        Image(
            painter = painterResource(id = R.drawable.profilepic),
            contentDescription = "profile pic",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 95.dp, y = 112.dp)
                .requiredSize(size = 169.dp)
                .clip(shape = CircleShape)
                .fillMaxSize()
                .testTag("profilePicture"),
            contentScale = ContentScale.Crop
        )

        //camera icon button
        IconButton(
            onClick = { },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 222.dp,
                    y = 238.dp
                )
                .border(1.dp, Color(0xff0277bd), shape = CircleShape)
                .background(
                    color = Color(0xff0277bd),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Outlined.CameraAlt,
                contentDescription = "camera",
                tint = Color(0xffffffff),
            )
        }

        //username field
        var text by remember { mutableStateOf("SariJ") }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Username") },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 120.dp,
                    y = 312.dp
                )
                .requiredWidth(width = 120.dp)
                .requiredHeight(height = 50.dp)
                .testTag("profileUsernameField")
        )

        //navigation bar
        NavigationBar(mainState.navController)

    }

}
