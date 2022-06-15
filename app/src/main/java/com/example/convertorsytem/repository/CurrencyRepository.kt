package com.example.convertorsytem.repository

import com.example.convertorsytem.data.remote.api.BaseApi
import com.example.convertorsytem.data.remote.response.CurrencyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CurrencyRepository(private val baseApi : BaseApi) {

    suspend fun getCurrency() : Flow<Result<List<CurrencyResponse>>> = flow {
        val response = baseApi.getCurrency()

        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.flowOn(Dispatchers.IO)
}