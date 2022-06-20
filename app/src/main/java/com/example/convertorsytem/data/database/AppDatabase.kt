package com.example.convertorsytem.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.convertorsytem.data.database.dao.CardDao
import com.example.convertorsytem.data.database.entity.CardEntity

@Database(entities = [CardEntity::class] , version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun cardDao() : CardDao

    companion object {
        private var appDatabase : AppDatabase? = null

        @Synchronized
        fun getInstance(context : Context) : AppDatabase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context , AppDatabase::class.java , "my_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase!!
        }
    }
}