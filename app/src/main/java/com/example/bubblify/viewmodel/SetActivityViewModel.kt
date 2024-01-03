package com.example.bubblify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bubblify.model.Activity
import com.example.bubblify.model.Reference
import com.example.bubblify.service.StorageService
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetActivityViewModel @Inject constructor(
    val storageService: StorageService,
    val firestore: FirebaseFirestore
) : ViewModel() {

    private val _activities = MutableLiveData<List<Reference<Activity>>>()
    val activities: LiveData<List<Reference<Activity>>> = _activities

    suspend fun getActivitiesByGroupReference(groupId: String) {
        val groupReference =
            firestore.collection(StorageService.GROUP_COLLECTION)
                .document(groupId)
        _activities.value = storageService.getActivitiesInGroup(groupReference)

    }
}