package com.example.network.repository

import com.example.network.api.RetrofitInterfaces
import com.example.network.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: RetrofitInterfaces) {
    suspend fun getUsers(): List<User> = api.getUsers()
}