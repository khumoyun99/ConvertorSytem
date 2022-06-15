package com.example.convertorsytem.presentation.ui.nav_questions.models

import java.io.Serializable

data class Questions(
    val id : Int ,
    val questions : String ,
    val answer : String ,
    val data : String
):Serializable