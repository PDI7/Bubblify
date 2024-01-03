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
import com.google.firebase.firestore.DocumentReference
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

    private val _searchResults = MutableLiveData<List<Reference<User>>>()
    val searchResults: LiveData<List<Reference<User>>> get() = _searchResults

    fun searchMembers(searchQuery: String, groupReferenceString: String) {
        viewModelScope.launch {
            try {
                val value = storageService.getUsersFromSearch(searchQuery, groupReferenceString);
                _searchResults.value = value!!.sortedBy { it.data.username }
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
            }
        }
    }

    fun addMemberToGroup(
        userReference: DocumentReference,
        groupReferenceString: String
    ) {
        viewModelScope.launch {
            try {
                val res = storageService.addUserToGroup(userReference, groupReferenceString)
                Log.d("test", res.toString())
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
            }
        }
    }
}