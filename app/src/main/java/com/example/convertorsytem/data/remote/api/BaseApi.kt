package com.example.convertorsytem.data.remote.api

import com.example.convertorsytem.data.remote.response.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET

interface BaseApi {

    @GET(".")
    suspend fun getCurrency():Response<List<CurrencyResponse>>
}