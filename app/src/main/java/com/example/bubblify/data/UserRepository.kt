package com.example.bubblify.data

import com.example.bubblify.model.BeerResponse
import com.example.bubblify.model.User
import com.example.bubblify.service.RetrofitInstance

class UserRepository {
    private val userService = RetrofitInstance.userService

    suspend fun getUsers(): BeerResponse {
        return  userService.getUsers()
    }
}