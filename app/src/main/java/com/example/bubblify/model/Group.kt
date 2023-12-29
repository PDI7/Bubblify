package com.example.bubblify.model

data class Group(
    val name: String,
    val color: Long,// Datatype TBD
) {
    constructor() : this("", 0L)  // Explicit no-argument constructor
}