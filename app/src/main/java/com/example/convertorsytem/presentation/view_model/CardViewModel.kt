package com.example.convertorsytem.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convertorsytem.data.database.entity.CardEntity
import com.example.convertorsytem.repository.DatabaseRepository
import com.example.convertorsytem.utils.NetworkHelper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CardViewModel(
    private val databaseRepository : DatabaseRepository ,
    private val networkHelper : NetworkHelper

):ViewModel() {

    val addCardDBLiveData = MutableLiveData<Boolean>()
    val getAllCardInfoLivaData = MutableLiveData<List<CardEntity>>()
    val errorLiveData = MutableLiveData<String>()

     fun addCardDB(cardEntity : CardEntity){
        if(networkHelper.isNetworkConnected()){

            databaseRepository.addCardDB(cardEntity)
            addCardDBLiveData.value = true
        }else{
            errorLiveData.value = "No Internet Connection"
        }
    }

    suspend fun getAllCardInfo(){
        if(networkHelper.isNetworkConnected()){
            databaseRepository.getAllCardInfo().onEach {
                it.onSuccess {
                    getAllCardInfoLivaData.value = it
                }
                it.onFailure {
                    errorLiveData.value = "Database is not working"
                }
            }.launchIn(viewModelScope)
        }else{
            errorLiveData.value = "No internet connection"
        }
    }
}