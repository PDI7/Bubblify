package com.example.bubblify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bubblify.data.UserRepository
import com.example.bubblify.model.BeerResponse
import com.example.bubblify.model.User
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _users = MutableLiveData<BeerResponse>()
    val users: LiveData<BeerResponse> = _users

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val value = repository.getUsers()
                _users.value = value
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}