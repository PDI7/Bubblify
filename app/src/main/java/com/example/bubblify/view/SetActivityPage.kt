package com.example.bubblify.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SportsFootball
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.SetActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetActivityPage(
    mainState: MainState,
    setActivityViewModel: SetActivityViewModel = hiltViewModel()
) {

    Box(
        Modifier
            .fillMaxSize()
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
                IconButton(onClick = { mainState.navigate("bubbleMain") }) {
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
                .offset(
                    x = 270.dp,
                    y = 90.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 87.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color(0xff7a40e8))
            )
            Icon(
                Icons.Outlined.SportsFootball,
                "Exercise",
                modifier = Modifier.size(128.dp),
                tint = Color.White
            )

        }

        NavigationBar(mainState.navController)
    }
}
