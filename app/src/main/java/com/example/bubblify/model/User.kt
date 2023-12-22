package com.example.bubblify.model

data class User(
    val uuid: String,
    val username: String,
    val email: String,
    var userGroups: List<UserGroup> = emptyList()
)
