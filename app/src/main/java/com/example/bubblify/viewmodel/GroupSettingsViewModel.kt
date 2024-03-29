package com.example.bubblify.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bubblify.model.Activity
import com.example.bubblify.model.Reference
import com.example.bubblify.model.User
import com.example.bubblify.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupSettingsViewModel
@Inject
constructor(
    private val application: Application,
    private val storageService: StorageService
) : AndroidViewModel(application = application) {

    private val _users = MutableLiveData<List<Reference<User>>>()
    val users: LiveData<List<Reference<User>>> = _users

    private val _activities = MutableLiveData<List<Reference<Activity>>>()
    val activities: LiveData<List<Reference<Activity>>> = _activities


    // Fetch all groups from the database
    fun fetchUsers(groupReferenceString: String) {
        viewModelScope.launch {
            try {
                val value = storageService.getAllUsersFromGroup(groupReferenceString)
                _users.value = value.sortedBy { it.data.username }
            } catch (e: Exception) {
                // If there is an error, log it
                Log.e("GroupSettingsViewModel", "fetchUsers: ${e.message}")
            }
        }
    }

    fun fetchActivities(groupReferenceString: String) {
        viewModelScope.launch {
            try {
                val value = storageService.getActivitiesInGroup(groupReferenceString)
                _activities.value = value.sortedBy { it.data.name }
            } catch (e: Exception) {
                // If there is an error, log it
                Log.e("GroupSettingsViewModel", "fetchUsers: ${e.message}")
            }
        }
    }

    fun removeUser(userReference: Reference<User>, groupReferenceString: String) {
        viewModelScope.launch {
            try {
                storageService.removeUserFromGroup(userReference.reference, groupReferenceString)
            } catch (e: Exception) {
                Log.e("GroupSettingsViewModel", "removeUser: ${e.message}")
            }
        }
    }

    fun removeActivity(activityReference: Reference<Activity>) {
        viewModelScope.launch {
            try {
                storageService.removeActivityFromGroup(activityReference.reference)
            } catch (e: Exception) {
                Log.e("GroupSettingsViewModel", "removeUser: ${e.message}")
            }
        }
    }
}