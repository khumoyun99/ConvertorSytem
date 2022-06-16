package com.example.convertorsytem.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0 ,
    val money : Double ,
    val number : String ,
    val holderName : String ,
    val date : String
)