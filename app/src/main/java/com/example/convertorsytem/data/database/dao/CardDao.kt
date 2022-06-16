package com.example.convertorsytem.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.convertorsytem.data.database.entity.CardEntity

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(cardEntity : CardEntity)

    @Query("select * from cardentity")
    fun getAllCardInfo() : List<CardEntity>
}