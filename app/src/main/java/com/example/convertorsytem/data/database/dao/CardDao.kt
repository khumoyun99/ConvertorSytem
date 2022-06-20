package com.example.convertorsytem.data.database.dao

import androidx.room.*
import com.example.convertorsytem.data.database.entity.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(cardEntity : CardEntity)

    @Query("select * from cardentity")
    fun getAllCardInfo() :List<CardEntity>

//    @Delete
//    fun deleteCard(cardEntity : CardEntity)
}