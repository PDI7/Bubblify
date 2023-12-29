package com.example.bubblify.model

data class Group(
    val name: String,
    val color: String,// Datatype TBD
) {
    constructor() : this("", "")  // Explicit no-argument constructor
}