package com.example.bubblify.model

data class User(
    val id: String,
    val email: String,
    val userGroups: List<UserGroup> = emptyList()
)
