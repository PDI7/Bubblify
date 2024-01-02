package com.example.bubblify.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bubblify.model.Group
import com.example.bubblify.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val application: Application,
    private val storageService: StorageService
) : AndroidViewModel(application = application) {

    private val _groups = MutableLiveData<List<Group>>()
    val groups: LiveData<List<Group>> = _groups


    // Fetch all groups from the database
    fun fetchGroups() {
        viewModelScope.launch {
            try {
                // Get all groups from the current user
                val value = storageService.getAllGroupsFromCurrentUser()
                _groups.value = value
            } catch (e: Exception){
                // If there is an error, log it
                Log.d("HomeViewModel", "fetchGroups: ${e.message}")
            }
        }
    }

    fun createGroup() {
        viewModelScope.launch {

            try {
                var groups = storageService.getGroups()
                Log.d("beforeCreation", groups?.size.toString())

                var groupName = "New Group"
                var count = 0

                // keep incrementing the count until a unique name is available
                while (groups!!.any { it.name == groupName }) {
                    count++
                    groupName = "New Group ($count)"
                }

                val group = Group(groupName, 4294902000)
                Log.d("name", group.name)
                val groupCreated = storageService.createGroup(group)

                Log.d("groupCreated", groupCreated?.get()?.await().toString())

                groups = storageService.getGroups()
                Log.d("afterCreation", groups?.size.toString())

            } catch (e: Exception) {
                Log.d("error", e.message.toString())
            }
        }
    }
}

