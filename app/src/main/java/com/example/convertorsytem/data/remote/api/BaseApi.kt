package com.example.convertorsytem.data.remote.api

import com.example.convertorsytem.data.remote.response.CurrencyResponse
import com.example.convertorsytem.data.remote.response.ExchangeRateApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface BaseApi {

    @GET(".")
    suspend fun getCurrency() : Response<List<CurrencyResponse>>

    @GET
    suspend fun getExchangeCurrency(@Url url:String ) : Response<ExchangeRateApi>
}