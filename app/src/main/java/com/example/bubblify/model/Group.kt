package com.example.bubblify.model

data class Group(
    val name: String,
    val color: Long,
    val activityIds: List<String> = emptyList(),
    val userGroupIds: List<String> = emptyList()
) {
    constructor() : this("", 0L)
}