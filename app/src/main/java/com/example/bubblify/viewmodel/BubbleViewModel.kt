package com.example.bubblify.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bubblify.model.Activity
import com.example.bubblify.model.Group
import com.example.bubblify.model.Reference
import com.example.bubblify.model.User
import com.example.bubblify.model.UserGroup
import com.example.bubblify.service.StorageService
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BubbleViewModel
@Inject
constructor(
    private val application: Application,
    private val storageService: StorageService
) : ViewModel(){
    private val _activities = MutableLiveData<List<Reference<Activity>>>()
    val activities: LiveData<List<Reference<Activity>>> = _activities

    private val _users = MutableLiveData<List<Reference<User>>>()
    val users: LiveData<List<Reference<User>>> = _users

    private val _usersActivity = MutableLiveData<List<Reference<UserGroup>>>()
    val usersActivity: LiveData<List<Reference<UserGroup>>> = _usersActivity

    val usersByActivity = mutableStateMapOf<String, List<Reference<UserGroup>>>()


    // Fetch all activities from the group
    fun fetchActivities(groupId: String) {
        viewModelScope.launch {
            try {
                // Get all activities from a group
                val value = storageService.getActivities(groupId)
                _activities.value = value.sortedBy { it.data.name }
            } catch (e: Exception){
                // If there is an error, log it
                Log.d("BubbleViewModel", "fetchActivities: ${e.message}")
            }
        }
    }

    fun changeGroupName(newName: String, group: Group, groupId: String) {
        viewModelScope.launch {
            try {
                // Get all activities from a group
                group.name = newName
                storageService.updateGroup(groupId, group)
            } catch (e: Exception){
                // If there is an error, log it
                Log.d("BubbleViewModel", "changeGroupName: ${e.message}")
            }
        }
    }

    fun fetchUsers(groupReferenceString: String) {
        viewModelScope.launch {
            try {
                val value = storageService.getAllUsersFromGroup(groupReferenceString)
                _users.value = value.sortedBy { it.data.username }
            } catch (e: Exception) {
                // If there is an error, log it
                Log.e("BubbleViewModel", "fetchUsers: ${e.message}")
            }
        }
    }

    fun filterUsers(activityReference: DocumentReference) {
        viewModelScope.launch {
            try {
                val value = storageService.filterUsersGroupsFromActivities(activityReference)
                usersByActivity[activityReference.id] = value
                _usersActivity.value = value
            } catch (e: Exception) {
                // If there is an error, log it
                Log.e("BubbleViewModel", "fetchUsersGroups: ${e.message}")
            }
        }
    }
}
