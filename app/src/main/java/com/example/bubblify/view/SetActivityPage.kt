package com.example.bubblify.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SportsFootball
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bubblify.viewmodel.SetActivityViewModel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.bubblify.model.ActivityIcon
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.SetActivityViewModel

@SuppressLint("StateFlowValueCalledInComposition")
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
        LazyVerticalGrid(
            modifier = Modifier.padding(top = 60.dp),
            columns = GridCells.Fixed(3),
            content = {
            items(setActivityViewModel.activities.value.size){ index ->
                FilledIconButton(
                    modifier = Modifier.aspectRatio(1f).padding(8.dp),
                    onClick = {
                              /*TODO*/
                    },
                ) {
                    setActivityViewModel.activities.value[index].data.icon?.let { painterResource(id = it.icon) }
                        ?.let { Icon(painter = it, contentDescription = "Sleeping") }
                }
            }
        })

        NavigationBar(mainState.navController)
    }
}
