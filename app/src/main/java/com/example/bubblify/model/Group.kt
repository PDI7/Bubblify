package com.example.bubblify.model

import androidx.compose.ui.graphics.Color

data class Group(
    val id: Int,
    val name: String,
    val color: Long
    val activities: List<Activity> = emptyList(),
    val userGroups: List<UserGroup> = emptyList()
)