package com.example.bubblify.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bubblify.model.Activity
import com.example.bubblify.model.Reference
import com.example.bubblify.service.StorageService
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetActivityViewModel @Inject constructor(
    val storageService: StorageService,
    val firestore: FirebaseFirestore
) : ViewModel() {
    private val _activities = MutableStateFlow<List<Reference<Activity>>>(listOf())
    val activities : StateFlow<List<Reference<Activity>>> get() = _activities
    /* lateinit var activities : MutableState<List<Reference<Activity>>> */

    init {
        getActivitiesByGroupReference()
    }
    private fun getActivitiesByGroupReference() {
        viewModelScope.launch {
            val groupReference =
                firestore.collection(StorageService.GROUP_COLLECTION).document("nLCaoyJWKw59XNuf0Frj")
            _activities.value = storageService.getActivitiesInGroup(groupReference)
        }
    }
}