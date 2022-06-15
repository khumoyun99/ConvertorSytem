package com.example.convertorsytem.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convertorsytem.data.remote.response.CurrencyResponse
import com.example.convertorsytem.repository.CurrencyRepository
import com.example.convertorsytem.utils.NetworkHelper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CurrencyViewModel(
    private val currencyRepository : CurrencyRepository ,
    private val networkHelper : NetworkHelper
):ViewModel() {

    val getCurrencyLiveData = MutableLiveData<List<CurrencyResponse>>()
    val errorLiveData = MutableLiveData<String>()

    suspend fun getCurrency() {
        if (networkHelper.isNetworkConnected()) {
            currencyRepository.getCurrency().onEach { result ->
                result.onSuccess {
                    val currencyResponseList = ArrayList(it)
                    for (currencyResponse in currencyResponseList) {
                        val path = "https://flagcdn.com/48x36/${currencyResponse.code?.substring(0,2)?.lowercase()}.png"
                        currencyResponse.photoUrl = path
                    }
                    getCurrencyLiveData.value = currencyResponseList
                }
                result.onFailure {
                    errorLiveData.value = it.message
                }
            }.launchIn(viewModelScope)
        } else {
            errorLiveData.value = "No internet connection"
        }
    }
}