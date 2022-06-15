package com.example.convertorsytem.data.remote.response

data class CurrencyResponse(
    val cb_price : String? = null ,
    val code : String? = null ,
    val date : String? = null ,
    var nbu_buy_price : String? = null ,
    var nbu_cell_price : String? = null ,
    val title : String? = null ,
    var photoUrl : String? = null
)