package com.example.bubblify.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bubblify.model.Reference
import com.example.bubblify.model.User
import com.example.bubblify.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val application: Application,
    private val storageService: StorageService
) : AndroidViewModel(application = application) {

    private val _user = MutableLiveData<Reference<User>>()
    val user: LiveData<Reference<User>> = _user

    fun fetchUser() {
        viewModelScope.launch {
            try {
                // Get all groups from the current user
                val value = storageService.getCurrentUser()
                _user.value = value
            } catch (e: Exception){
                // If there is an error, log it
                Log.d("HomeViewModel", "fetchGroups: ${e.message}")
            }
        }
    }
}