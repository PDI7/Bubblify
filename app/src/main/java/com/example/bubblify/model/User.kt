package com.example.bubblify.model

data class User(
    val uuid: String,
    val username: String,
    val email: String,
    var userGroupIds: List<String> = emptyList()
)
