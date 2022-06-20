package com.example.convertorsytem.presentation.ui.nav_currency.screns

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.convertorsytem.R
import com.example.convertorsytem.data.database.AppDatabase
import com.example.convertorsytem.data.remote.ApiClient
import com.example.convertorsytem.data.remote.response.CurrencyResponse
import com.example.convertorsytem.databinding.ScreenCurrencyConvertorBinding
import com.example.convertorsytem.presentation.ui.nav_currency.adapter.CurrencySpinnerAdapter
import com.example.convertorsytem.presentation.ui.nav_currency.models.Currency
import com.example.convertorsytem.presentation.view_model.CurrencyViewModel
import com.example.convertorsytem.presentation.view_model.CurrencyViewModelFactory
import com.example.convertorsytem.repository.CurrencyRepository
import com.example.convertorsytem.repository.DatabaseRepository
import com.example.convertorsytem.utils.NetworkHelper
import com.example.convertorsytem.utils.scope
import com.example.convertorsytem.utils.showToast
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CurrencyConvertorScreen:Fragment(R.layout.screen_currency_convertor) {

    private val binding by viewBinding(ScreenCurrencyConvertorBinding::bind)
    private lateinit var currencySpinnerAdapter : CurrencySpinnerAdapter
    private lateinit var currencySpinner1Adapter : CurrencySpinnerAdapter
    private lateinit var currencyViewModel : CurrencyViewModel
    private lateinit var currencyList : ArrayList<Currency>
    private lateinit var currencyList1 : ArrayList<Currency>
    private val args : CurrencyConvertorScreenArgs by navArgs()

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        currencyViewModel = ViewModelProvider(
            this@CurrencyConvertorScreen , CurrencyViewModelFactory(
                CurrencyRepository(ApiClient.apiService) ,
                NetworkHelper(requireContext()) ,
                DatabaseRepository(AppDatabase.getInstance(requireContext()).cardDao())
            )
        )[CurrencyViewModel::class.java]


        loadData()

        exchangeImg.setOnClickListener {
            val price = currencyValue.text.toString()
            if (price.isNotEmpty()) {
                val spinnerValue = currencySp.selectedItemPosition
                val spinnerValue1 = currencySp1.selectedItemPosition
                var value : Double = 0.0
                try {
                    if (spinnerValue1 == args.position) {
                        value = price.toInt() * currencyList[spinnerValue].cb_price!!.toDouble()
                    } else {
                        value =
                            price.toInt() * currencyList[spinnerValue].cb_price!!.toDouble() / currencyList1[spinnerValue1].cb_price!!.toDouble()
                    }
                    tvValue.text = String.format("%1.2f" , value)

                } catch (e : Exception) {
                    showToast("Out of limit")
                }


            } else {
                showToast("Enter price!")
            }
        }

    }

    private fun loadData() {
        lifecycleScope.launch {
            currencyViewModel.getCurrency()
        }

        currencyViewModel.getCurrencyLiveData.observe(
            viewLifecycleOwner ,
            getCurrencyObserver
        )

        currencyViewModel.errorLiveData.observe(
            viewLifecycleOwner ,
            errorObserver
        )
    }

    private val getCurrencyObserver = Observer<List<CurrencyResponse>> {
        currencyList = ArrayList()
        it.forEach { it ->
            currencyList.add(
                Currency(
                    cb_price = it.cb_price ,
                    code = it.code ,
                    date = it.date ,
                    nbu_buy_price = it.nbu_buy_price ,
                    nbu_cell_price = it.nbu_cell_price ,
                    title = it.title ,
                    photoUrl = it.photoUrl
                )
            )
        }
        currencySpinnerAdapter = CurrencySpinnerAdapter()
        currencySpinnerAdapter.mySubmitList(currencyList)

        binding.apply {
            currencySp.adapter = currencySpinnerAdapter
            currencySp.setSelection(args.position)
        }


        //2-spinner
        currencyList1 = ArrayList(currencyList)
        val currencyUzb = Currency(
            cb_price = "-" ,
            code = "UZS" ,
            nbu_buy_price = "-" ,
            nbu_cell_price = "-" ,
            title = "UZB so'mi" ,
            photoUrl = "https://flagcdn.com/48x36/uz.png"
        )
        currencyList1[args.position] = currencyUzb
        currencySpinner1Adapter = CurrencySpinnerAdapter()
        currencySpinner1Adapter.mySubmitList(currencyList1)

        binding.apply {
            currencySp1.adapter = currencySpinner1Adapter
        }



        binding.currencySp1.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent : AdapterView<*>? ,
                view : View? ,
                position : Int ,
                id : Long
            ) {

                if (currencyList1[position].nbu_buy_price!!.isNotEmpty()) {
                    binding.buyTv.text = currencyList1[position].nbu_buy_price.toString()
                } else {
                    binding.buyTv.text = currencyList1[position].cb_price.toString()
                }

                if (currencyList1[position].nbu_cell_price!!.isNotEmpty()) {
                    binding.sellTv.text = currencyList1[position].nbu_cell_price.toString()
                } else {
                    binding.sellTv.text = currencyList1[position].cb_price.toString()
                }
            }

            override fun onNothingSelected(parent : AdapterView<*>?) {

            }
        }


    }

    private val errorObserver = Observer<String> {
        Snackbar.make(binding.root , it , Snackbar.LENGTH_SHORT)
    }

}