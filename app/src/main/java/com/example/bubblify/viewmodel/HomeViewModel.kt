package com.example.bubblify.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bubblify.model.Activity
import com.example.bubblify.model.ActivityIcon
import com.example.bubblify.model.Group
import com.example.bubblify.model.Reference
import com.example.bubblify.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val application: Application,
    private val storageService: StorageService
) : AndroidViewModel(application = application) {

    private val _groups = MutableLiveData<List<Reference<Group>>>()
    val groups: LiveData<List<Reference<Group>>> = _groups


    // Fetch all groups from the database
    fun fetchGroups() {
        viewModelScope.launch {
            try {
                // Get all groups from the current user
                val value = storageService.getAllGroupsWithReferenceFromCurrentUser()
                _groups.value = value.sortedBy { it.data.name }
            } catch (e: Exception){
                // If there is an error, log it
                Log.d("HomeViewModel", "fetchGroups: ${e.message}")
            }
        }
    }

    fun createGroup() {
        viewModelScope.launch {

            try {
                val groups = storageService.getGroups()

                var groupName = "New Group"
                var count = 0

                // keep incrementing the count until a unique name is available
                while (groups!!.any { it.name == groupName }) {
                    count++
                    groupName = "New Group ($count)"
                }

                val group = Group(groupName, 4294902000)

                val groupCreated = storageService.createGroup(group)

                val defaultActivities = listOf(
                    Activity(groupCreated, "Eat", ActivityIcon.EATING),
                    Activity(groupCreated, "Sleep", ActivityIcon.SLEEPING),
                    Activity(groupCreated, "Work", ActivityIcon.WORKING),
                )

                defaultActivities.forEach { defaultActivity ->
                    storageService.addActivityToGroup(defaultActivity, groupCreated.id)
                }

            } catch (e: Exception) {
                Log.d("error", e.message.toString())
            }
        }
    }
}

