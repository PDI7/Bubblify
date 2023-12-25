package com.example.bubblify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bubblify.model.Group
import com.example.bubblify.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class BubbleListViewModel
@Inject
constructor(
    private val application: Application,
    private val storageService: StorageService
) : AndroidViewModel(application = application){


    private val _groups = MutableLiveData<List<Group>>()
    val groups: LiveData<List<Group>> = _groups


    // Fetch all groups from the database
    fun fetchGroups() {
        viewModelScope.launch {
            try {
                // Get all groups from the database
                val value = storageService.getAllGroups()
                _groups.value = value
            } catch (e: Exception){
                // If there is an error, log it
                Log.d("BubbleListViewModel", "fetchGroups: ${e.message}")
            }
        }
    }

}