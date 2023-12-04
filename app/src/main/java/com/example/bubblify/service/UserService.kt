package com.example.bubblify.service

import com.example.bubblify.model.BeerResponse
import com.example.bubblify.model.User
import retrofit2.http.GET
interface UserService {
    @GET("beers")
    suspend fun getUsers(): BeerResponse
}