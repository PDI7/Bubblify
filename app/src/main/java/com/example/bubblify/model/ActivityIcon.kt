package com.example.bubblify.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.bubblify.R

enum class ActivityIcon {
    WORKING,
    EATING,
    SLEEPING,
    SPORT,
    PARTY,
    STUDY,
    CHILLING,
    BORED,
    GAMING,
    COOKING,
    FAMILY,
    BUSY,
    SHOPPING,
    MUSIC;

    val icon
    @Composable
    @ReadOnlyComposable
    get() = when (this){
        WORKING -> R.drawable.baseline_computer_24
        EATING -> R.drawable.baseline_bakery_dining_24
        SLEEPING -> R.drawable.baseline_airline_seat_individual_suite_24
        SPORT -> R.drawable.baseline_sports_basketball_24
        PARTY -> R.drawable.baseline_celebration_24
        STUDY -> R.drawable.baseline_menu_book_24
        CHILLING -> R.drawable.baseline_tv_24
        BORED -> R.drawable.baseline_thumb_down_24
        GAMING -> R.drawable.baseline_sports_esports_24
        COOKING -> R.drawable.baseline_lunch_dining_24
        FAMILY -> R.drawable.baseline_family_restroom_24
        BUSY -> R.drawable.baseline_do_not_disturb_on_24
        SHOPPING -> R.drawable.baseline_shopping_bag_24
        MUSIC -> R.drawable.baseline_headphones_24
    }
}