package com.example.convertorsytem.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.convertorsytem.repository.CurrencyRepository
import com.example.convertorsytem.utils.NetworkHelper

class CurrencyViewModelFactory(
    private val currencyRepository : CurrencyRepository ,
    private val networkHelper : NetworkHelper
):ViewModelProvider.Factory {
    override fun <T:ViewModel?> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            return CurrencyViewModel(currencyRepository , networkHelper) as T
        }
        throw Exception("view model error")
    }
}