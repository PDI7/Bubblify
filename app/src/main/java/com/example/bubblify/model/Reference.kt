package com.example.bubblify.model

import com.google.firebase.firestore.DocumentReference

data class Reference<T>(
    val reference: DocumentReference,
    val data: T
)