package com.example.network.client

import com.example.network.api.RetrofitInterfaces
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitClient {

    private val okHttpClient = OkHttpClient.Builder().build()

    val apiService: RetrofitInterfaces by lazy {
        Retrofit.Builder()
            .baseUrl("https://fake-json-api.mock.beeceptor.com/users/") // notice the slash at the end
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterfaces::class.java)
    }
}


