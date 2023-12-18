package com.example.bubblify.model

data class Group(
    val id: String,
    val name: String,
    val color: String,// Datatype TBD
    val activities: List<Activity> = emptyList(),
    val userGroups: List<UserGroup> = emptyList()
)