package com.example.bubblify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bubblify.model.Activity
import com.example.bubblify.model.Reference
import com.example.bubblify.service.AccountService
import com.example.bubblify.service.StorageService
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetActivityViewModel @Inject constructor(
    val accountService: AccountService,
    val storageService: StorageService,
    val firestore: FirebaseFirestore
) : ViewModel() {

    private val _activities = MutableLiveData<List<Reference<Activity>>>()
    val activities: LiveData<List<Reference<Activity>>> = _activities

    suspend fun getActivitiesByGroupReference(groupId: String) {
        _activities.postValue(storageService.getActivitiesInGroup(groupId))

    }

    /*
    activityReference: DocumentReference?,
        userReference: DocumentReference,
        groupReference: DocumentReference
    */
    suspend fun setActivityForUserInGroup(
        groupId: String,
        activityReference: DocumentReference?
    ){
        val groupReference: DocumentReference =
            firestore.collection(StorageService.GROUP_COLLECTION)
                .document(groupId)
        println("HERE.....: ${accountService.currentUserId}")
        val userReference =
            firestore.collection(StorageService.USER_COLLECTION).document(accountService.currentUserId)

        storageService.setActivityForUserInGroup(activityReference, userReference, groupReference)
    }
}