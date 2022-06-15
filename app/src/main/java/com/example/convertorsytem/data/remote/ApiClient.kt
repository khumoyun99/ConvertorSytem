package com.example.convertorsytem.data.remote

import com.example.convertorsytem.data.remote.api.BaseApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {


    private const val BASE_URL = "https://nbu.uz/uz/exchange-rates/json/"

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService:BaseApi = getRetrofit().create(BaseApi::class.java)
}