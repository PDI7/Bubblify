package com.example.bubblify.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bubblify.MainState
import com.example.bubblify.view.common.NavigationBar
import com.example.bubblify.viewmodel.SetActivityViewModel
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetActivityPage(
    mainState: MainState,
    groupId: String?,
    setActivityViewModel: SetActivityViewModel = hiltViewModel()
) {
    val activityList by setActivityViewModel.activities.observeAsState(null)

    // Get the data before starting the UI
    LaunchedEffect(Unit) {
        if (groupId != null)
            setActivityViewModel.getActivitiesByGroupReference(groupId)
        else
            Log.e("SetActivityPage", "groupId is null")
    }

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
                    modifier = Modifier.testTag("setActivityTitle")
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { mainState.navigate("bubbleMain/${groupId}") },
                    modifier = Modifier.testTag("setActivityBackButton")
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "get back"
                    )
                }
            }
        )

        val coroutine = rememberCoroutineScope()
        if (activityList == null) {
            // Waiting for the data (and avoid app crash)
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .align(Alignment.TopCenter),
                color = MaterialTheme.colorScheme.secondary
            )
        } else {
            LazyVerticalGrid(
                modifier = Modifier.padding(top = 60.dp),
                columns = GridCells.Fixed(3),
                content = {
                    items(activityList!!.size) { index ->
                        FilledIconButton(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(8.dp)
                                .testTag("activityButton${activityList!![index].data.name}"),
                            onClick = {
                                if (groupId != null) {
                                    coroutine.launch {
                                        setActivityViewModel.setActivityForUserInGroup(
                                            groupId,
                                            activityList!![index].reference
                                        )
                                        mainState.navigate("bubbleMain/${groupId}")
                                    }
                                }
                            },
                        ) {
                            activityList!![index].data.icon?.let {
                                painterResource(
                                    id = it.icon
                                )
                            }
                                ?.let { Icon(painter = it, contentDescription = "Sleeping") }
                        }
                    }
                })
        }

        NavigationBar(mainState.navController)
    }
}
