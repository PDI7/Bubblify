package com.example.bubblify.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.bubblify.R

enum class ActivityIcon {
    WORKING,
    EATING,
    SLEEPING,
    SPORT;

    val icon
    @Composable
    @ReadOnlyComposable
    get() = when (this){
        WORKING -> R.drawable.baseline_computer_24
        EATING -> R.drawable.baseline_bakery_dining_24
        SLEEPING -> R.drawable.baseline_airline_seat_individual_suite_24
        SPORT -> R.drawable.baseline_sports_basketball_24
    }
}