package com.example.bubblify.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bubblify.model.Activity
import com.example.bubblify.model.Group
import com.example.bubblify.service.StorageService
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
    private val _activities = MutableLiveData<List<Activity>>()
    val activities: LiveData<List<Activity>> = _activities


    // Fetch all activities from the group
    fun fetchActivities() {
        viewModelScope.launch {
            try {
                // Get all activities from a group
                val value = storageService.getActivities("nLCaoyJWKw59XNuf0Frj")
                _activities.value = value
            } catch (e: Exception){
                // If there is an error, log it
                Log.d("BubbleViewModel", "fetchGroups: ${e.message}")
            }
        }
    }
}
