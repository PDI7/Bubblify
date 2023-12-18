package com.example.bubblify.view
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SportsFootball
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip

@Composable
fun NavBarUI() {
    var selectedItem by remember { mutableIntStateOf(0) }
    Box( // Navigation bar at the bottom, this box is NEEDED
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = selectedItem == 0,
                onClick = { selectedItem = 0 }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
                label = { Text("Profile") },
                selected = selectedItem == 1,
                onClick = { selectedItem = 1 }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Settings, contentDescription = "More") },
                label = { Text("More") },
                selected = selectedItem == 2,
                onClick = { selectedItem = 2 })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetActivityPage(setActivityViewModel: SetActivityViewModel) {

    Box(
        Modifier
            .requiredWidth(width = 360.dp)
            .requiredHeight(height = 640.dp)
            .background(color = Color.White)
    ) {
        //other stuff
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Set Activity",
                    maxLines = 1,
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "get back"
                    )
                }
            }
        )

        IconButton(
            onClick = { },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 156.dp, y = 90.dp)
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 87.dp)
                    .requiredSize(size = 87.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color(0xff7a40e8))
            )
            Icon(
                Icons.Outlined.School,
                "School",
                modifier = Modifier.size(128.dp),
                tint = Color.White
            )

        }
        IconButton(
            onClick = { },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 42.dp, y = 90.dp)
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 87.dp)
                    .requiredSize(size = 87.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color(0xff7a40e8))
            )
            Icon(
                Icons.Outlined.WorkOutline,
                "Work",
                modifier = Modifier.size(128.dp),
                tint = Color.White
                )
        }
        IconButton(
            onClick = { },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 270.dp,
                    y = 90.dp)
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 87.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color(0xff7a40e8)))
            Icon(
                Icons.Outlined.SportsFootball,
                "Exercise",
                modifier = Modifier.size(128.dp),
                tint = Color.White
            )

        }

        NavBarUI()
    }
}


@Preview(widthDp = 360, heightDp = 640)
@Composable
fun SetActivityPreview() {
    SetActivityPage(setActivityViewModel = SetActivityViewModel())
}
