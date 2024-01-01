package com.example.bubblify.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bubblify.model.User
import com.example.bubblify.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMembersViewModel
@Inject
constructor(
    private val application: Application,
    private val storageService: StorageService
) : AndroidViewModel(application = application) {

    private val _searchResults = MutableLiveData<List<User>>()
    val searchResults: LiveData<List<User>> get() = _searchResults

    fun searchMembers(searchQuery: String) {
        viewModelScope.launch {
            try {
                val users = storageService.getUsersFromSearch(searchQuery);
                _searchResults.value = users
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
            }
        }
    }
}