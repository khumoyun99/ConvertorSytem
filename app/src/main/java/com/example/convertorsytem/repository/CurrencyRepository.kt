package com.example.convertorsytem.repository

import com.example.convertorsytem.data.remote.api.BaseApi
import com.example.convertorsytem.data.remote.response.CurrencyResponse
import com.example.convertorsytem.data.remote.response.ExchangeRateApi
import com.example.convertorsytem.presentation.view_model.CurrencyViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class CurrencyRepository(private val baseApi : BaseApi) {

    suspend fun getCurrency() : Flow<Result<List<CurrencyResponse>>> = flow {
        try {
            val response = baseApi.getCurrency()

            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Throwable("Error response")))
            }
        } catch (e : Exception) {
            emit(Result.failure(Throwable(e.message)))
        }

    }.flowOn(Dispatchers.IO)

    suspend fun getExchangeCurrency() : Flow<Result<ExchangeRateApi>> = flow {
        try {
            val response = baseApi.getExchangeCurrency("https://v6.exchangerate-api.com/v6/dcc73ef8467e64cae169527c/latest/UZS")
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Throwable("error response")))
            }

        } catch (e : Exception) {
            emit(Result.failure(Throwable(e.message)))
        }
    }.flowOn(Dispatchers.IO)


}