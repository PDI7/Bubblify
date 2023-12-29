package com.example.bubblify.model

data class Group(
    val name: String = "",
    val color: Long = 0xff000000,
    val activityIds: List<String> = emptyList(),
    val userGroupIds: List<String> = emptyList()
)