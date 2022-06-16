package com.example.convertorsytem.repository

import com.example.convertorsytem.data.database.dao.CardDao
import com.example.convertorsytem.data.database.entity.CardEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DatabaseRepository(private val cardDao : CardDao) {

    fun addCardDB(cardEntity : CardEntity) = cardDao.addCard(cardEntity)

    suspend fun getAllCardInfo() :Flow<Result<List<CardEntity>>> = flow {
        val data = cardDao.getAllCardInfo()

        if(data.isNotEmpty()){
            emit(Result.success(data))
        }else{
            emit(Result.failure(Throwable("Database empty")))
        }
    }.flowOn(Dispatchers.IO)


}