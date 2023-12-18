package com.example.bubblify.view
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SportsFootball
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bubblify.viewmodel.SetActivityViewModel
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.bubblify.R
import com.example.bubblify.view.NavBarUI
import com.example.bubblify.viewmodel.ProfilePageViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(profilePageViewModel: ProfilePageViewModel) {

    //screen
    Box(
        Modifier
            .requiredWidth(width = 360.dp)
            .requiredHeight(height = 640.dp)
            .background(color = Color.White)
    ) {

        //top bar
        CenterAlignedTopAppBar(
            title = { Text("Profile", maxLines = 1) },
            navigationIcon = {
                IconButton(onClick = { /* do something */ }) {
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
        )

        //camera icon button
        IconButton(
            onClick = { },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 222.dp,
                    y = 238.dp)
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 51.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color(0xff0277bd))
                    .offset(x=213.dp, y=230.dp)
            )
            Icon(
                imageVector = Icons.Outlined.CameraAlt,
                contentDescription = "camera",
                //tint = Color(0xffffff) //????????????????????
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
                .offset(x = 120.dp,
                    y = 312.dp)
                .requiredWidth(width = 120.dp)
                .requiredHeight(height = 50.dp))

        //navigation bar
        NavBarUI()

    }

}


@Preview(widthDp = 360, heightDp = 640)
@Composable
fun ProfilePagePreview() {
    ProfilePage(profilePageViewModel = ProfilePageViewModel())
}