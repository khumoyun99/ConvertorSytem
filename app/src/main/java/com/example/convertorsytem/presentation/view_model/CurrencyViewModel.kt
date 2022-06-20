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
                        val path = "https://flagcdn.com/48x36/${
                            currencyResponse.code?.substring(0 , 2)?.lowercase()
                        }.png"
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

    suspend fun getExchangeCurrency() {
        if (networkHelper.isNetworkConnected()) {
            currencyRepository.getExchangeCurrency().onEach { result ->
                result.onSuccess {
                    val exchangeRateApi = it
                    val codeList = arrayListOf(
                        "AED" ,
                        "AFN" ,
                        "ALL" ,
                        "AMD" ,
                        "ANG" ,
                        "AOA" ,
                        "ARS" ,
                        "AUD" ,
                        "AWG" ,
                        "AZN" ,
                        "BAM" ,
                        "BBD" ,
                        "BDT" ,
                        "BGN" ,
                        "BHD" ,
                        "BIF" ,
                        "BMD" ,
                        "BND" ,
                        "BOB" ,
                        "BRL" ,
                        "BSD" ,
                        "BTN" ,
                        "BWP" ,
                        "BYN" ,
                        "BZD" ,
                        "CAD" ,
                        "CDF" ,
                        "CHF" ,
                        "CLP" ,
                        "CNY" ,
                        "COP" ,
                        "CRC" ,
                        "CUP" ,
                        "CVE" ,
                        "CZK" ,
                        "DJF" ,
                        "DKK" ,
                        "DOP" ,
                        "DZD" ,
                        "EGP" ,
                        "ERN" ,
                        "ETB" ,
                        "EUR" ,
                        "FJD" ,
                        "FKP" ,
                        "FOK" ,
                        "GBP" ,
                        "GEL" ,
                        "GGP" ,
                        "GHS" ,
                        "GIP" ,
                        "GMD" ,
                        "GNF" ,
                        "GTQ" ,
                        "GYD" ,
                        "HKD" ,
                        "HNL" ,
                        "HRK" ,
                        "HTG" ,
                        "HUF" ,
                        "IDR" ,
                        "ILS" ,
                        "IMP" ,
                        "INR" ,
                        "IQD" ,
                        "IRR" ,
                        "ISK" ,
                        "JEP" ,
                        "JMD" ,
                        "JOD" ,
                        "JPY" ,
                        "KES" ,
                        "KGS" ,
                        "KHR" ,
                        "KID" ,
                        "KMF" ,
                        "KRW" ,
                        "KWD" ,
                        "KYD" ,
                        "KZT" ,
                        "LAK" ,
                        "LBP" ,
                        "LKR" ,
                        "LRD" ,
                        "LSL" ,
                        "LYD" ,
                        "MAD" ,
                        "MDL" ,
                        "MGA" ,
                        "MKD" ,
                        "MMK" ,
                        "MNT" ,
                        "MOP" ,
                        "MRU" ,
                        "MUR" ,
                        "MVR" ,
                        "MWK" ,
                        "MXN" ,
                        "MYR" ,
                        "MZN" ,
                        "NAD" ,
                        "NGN" ,
                        "NIO" ,
                        "NOK" ,
                        "NPR" ,
                        "NZD" ,
                        "OMR" ,
                        "PAB" ,
                        "PEN" ,
                        "PGK" ,
                        "PHP" ,
                        "PKR" ,
                        "PLN" ,
                        "PYG" ,
                        "QAR" ,
                        "RON" ,
                        "RSD" ,
                        "RUB" ,
                        "RWF" ,
                        "SAR" ,
                        "SBD" ,
                        "SCR" ,
                        "SDG" ,
                        "SEK" ,
                        "SGD" ,
                        "SHP" ,
                        "SLL" ,
                        "SOS" ,
                        "SRD" ,
                        "SSP" ,
                        "STN" ,
                        "SYP" ,
                        "SZL" ,
                        "THB" ,
                        "TJS" ,
                        "TMT" ,
                        "TND" ,
                        "TOP" ,
                        "TRY" ,
                        "TTD" ,
                        "TVD" ,
                        "TWD" ,
                        "TZS" ,
                        "UAH" ,
                        "UGX" ,
                        "USD" ,
                        "UYU" ,
                        "VES" ,
                        "VND" ,
                        "VUV" ,
                        "WST" ,
                        "XAF" ,
                        "XCD" ,
                        "XDR" ,
                        "XOF" ,
                        "XPF" ,
                        "YER" ,
                        "ZAR" ,
                        "ZMW" ,
                        "ZWL"
                    )
                    val currencyResponseList : ArrayList<CurrencyResponse> = ArrayList()

                    currencyResponseList.add(
                        CurrencyResponse(
                            cb_price = exchangeRateApi.conversion_rates.AED.toString() ,
                            code = codeList[0] ,
                            date = exchangeRateApi.time_last_update_utc ,
                            nbu_buy_price = exchangeRateApi.conversion_rates.AED.toString() ,
                            nbu_cell_price = exchangeRateApi.conversion_rates.AED.toString() ,
                            title = "UAE Dirham" ,
                            photoUrl = "https://flagcdn.com/48x36/${
                                codeList[0].substring(0 , 2).lowercase()
                            }.png"
                        )
                    )

                    currencyResponseList.add(
                        CurrencyResponse(
                            cb_price = exchangeRateApi.conversion_rates.AFN.toString() ,
                            code = codeList[1] ,
                            date = exchangeRateApi.time_last_update_utc ,
                            nbu_buy_price = exchangeRateApi.conversion_rates.AFN.toString() ,
                            nbu_cell_price = exchangeRateApi.conversion_rates.AFN.toString() ,
                            title = "Afghan Afghani" ,
                            photoUrl = "https://flagcdn.com/48x36/${
                                codeList[1].substring(0 , 2).lowercase()
                            }.png"
                        )
                    )

                    currencyResponseList.add(
                        CurrencyResponse(
                            cb_price = exchangeRateApi.conversion_rates.ALL.toString() ,
                            code = codeList[2] ,
                            date = exchangeRateApi.time_last_update_utc ,
                            nbu_buy_price = exchangeRateApi.conversion_rates.ALL.toString() ,
                            nbu_cell_price = exchangeRateApi.conversion_rates.ALL.toString() ,
                            title = "Afghan Afghani" ,
                            photoUrl = "https://flagcdn.com/48x36/${
                                codeList[2].substring(0 , 2).lowercase()
                            }.png"
                        )
                    )

                    currencyResponseList.add(
                        CurrencyResponse(
                            cb_price = exchangeRateApi.conversion_rates.AMD.toString() ,
                            code = codeList[3] ,
                            date = exchangeRateApi.time_last_update_utc ,
                            nbu_buy_price = exchangeRateApi.conversion_rates.AMD.toString() ,
                            nbu_cell_price = exchangeRateApi.conversion_rates.AMD.toString() ,
                            title = "Armenian Dram" ,
                            photoUrl = "https://flagcdn.com/48x36/${
                                codeList[3].substring(0 , 2).lowercase()
                            }.png"
                        )
                    )

                    currencyResponseList.add(
                        CurrencyResponse(
                            cb_price = exchangeRateApi.conversion_rates.ANG.toString() ,
                            code = codeList[4] ,
                            date = exchangeRateApi.time_last_update_utc ,
                            nbu_buy_price = exchangeRateApi.conversion_rates.ANG.toString() ,
                            nbu_cell_price = exchangeRateApi.conversion_rates.ANG.toString() ,
                            title = "Netherlands Antillian Guilder" ,
                            photoUrl = "https://flagcdn.com/48x36/${
                                codeList[4].substring(0 , 2).lowercase()
                            }.png"
                        )
                    )
                    currencyResponseList.add(
                        CurrencyResponse(
                            cb_price = exchangeRateApi.conversion_rates.AOA.toString() ,
                            code = codeList[5] ,
                            date = exchangeRateApi.time_last_update_utc ,
                            nbu_buy_price = exchangeRateApi.conversion_rates.AOA.toString() ,
                            nbu_cell_price = exchangeRateApi.conversion_rates.AOA.toString() ,
                            title = "Angolan Kwanza" ,
                            photoUrl = "https://flagcdn.com/48x36/${
                                codeList[5].substring(0 , 2).lowercase()
                            }.png"
                        )
                    )

                    currencyResponseList.add(
                        CurrencyResponse(
                            cb_price = exchangeRateApi.conversion_rates.ARS.toString() ,
                            code = codeList[6] ,
                            date = exchangeRateApi.time_last_update_utc ,
                            nbu_buy_price = exchangeRateApi.conversion_rates.ARS.toString() ,
                            nbu_cell_price = exchangeRateApi.conversion_rates.ARS.toString() ,
                            title = "Argentine Peso" ,
                            photoUrl = "https://flagcdn.com/48x36/${
                                codeList[6].substring(0 , 2).lowercase()
                            }.png"
                        )
                    )


                    currencyResponseList.add(
                        CurrencyResponse(
                            cb_price = exchangeRateApi.conversion_rates.AUD.toString() ,
                            code = codeList[7] ,
                            date = exchangeRateApi.time_last_update_utc ,
                            nbu_buy_price = exchangeRateApi.conversion_rates.AUD.toString() ,
                            nbu_cell_price = exchangeRateApi.conversion_rates.AUD.toString() ,
                            title = "Australian Dollar" ,
                            photoUrl = "https://flagcdn.com/48x36/${
                                codeList[7].substring(0 , 2).lowercase()
                            }.png"
                        )
                    )

                    currencyResponseList.add(
                        CurrencyResponse(
                            cb_price = exchangeRateApi.conversion_rates.AWG.toString() ,
                            code = codeList[7] ,
                            date = exchangeRateApi.time_last_update_utc ,
                            nbu_buy_price = exchangeRateApi.conversion_rates.AWG.toString() ,
                            nbu_cell_price = exchangeRateApi.conversion_rates.AWG.toString() ,
                            title = "Aruban Florin" ,
                            photoUrl = "https://flagcdn.com/48x36/${
                                codeList[8].substring(0 , 2).lowercase()
                            }.png"
                        )
                    )
                    currencyResponseList.add(
                        CurrencyResponse(
                            cb_price = exchangeRateApi.conversion_rates.AZN.toString() ,
                            code = codeList[9] ,
                            date = exchangeRateApi.time_last_update_utc ,
                            nbu_buy_price = exchangeRateApi.conversion_rates.AZN.toString() ,
                            nbu_cell_price = exchangeRateApi.conversion_rates.AZN.toString() ,
                            title = "Azerbaijani Manat" ,
                            photoUrl = "https://flagcdn.com/48x36/${
                                codeList[9].substring(0 , 2).lowercase()
                            }.png"
                        )
                    )

                    getCurrencyLiveData.value = currencyResponseList

                }
                result.onFailure {
                    errorLiveData.value = it.message
                }
            }.launchIn(viewModelScope)
        } else {
            errorLiveData.value = "No Internet connection"
        }
    }
}