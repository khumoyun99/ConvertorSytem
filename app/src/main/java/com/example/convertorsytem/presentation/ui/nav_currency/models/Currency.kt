package com.example.convertorsytem.presentation.ui.nav_currency.models

import java.io.Serializable

data class Currency(
    val cb_price : String? = null ,
    val code : String? = null ,
    val date : String? = null ,
    var nbu_buy_price : String? = null ,
    var nbu_cell_price : String? = null ,
    val title : String? = null ,
    var photoUrl : String? = null
):Serializable