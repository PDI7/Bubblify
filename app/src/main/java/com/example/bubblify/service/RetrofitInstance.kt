package com.example.bubblify.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // We can change with firebase after !!! It is just for the demo
    private const val API_URL = "https://random-data-api.com/api/v2/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}