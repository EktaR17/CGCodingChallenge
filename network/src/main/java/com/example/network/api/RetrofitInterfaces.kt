package com.example.network.api

import com.example.network.model.User
import retrofit2.http.GET

interface RetrofitInterfaces {
    @GET("users")
    suspend fun getUsers(): List<User>
}