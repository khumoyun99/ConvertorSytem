package com.example.convertorsytem.presentation.ui.nav_currency

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.convertorsytem.R
import com.example.convertorsytem.data.database.AppDatabase
import com.example.convertorsytem.data.remote.ApiClient
import com.example.convertorsytem.data.remote.response.CurrencyResponse
import com.example.convertorsytem.databinding.PageCurrencyBinding
import com.example.convertorsytem.presentation.ui.nav_currency.adapter.CurrencyRvAdapter
import com.example.convertorsytem.presentation.ui.nav_currency.models.Currency
import com.example.convertorsytem.presentation.view_model.CurrencyViewModel
import com.example.convertorsytem.presentation.view_model.CurrencyViewModelFactory
import com.example.convertorsytem.repository.CurrencyRepository
import com.example.convertorsytem.repository.DatabaseRepository
import com.example.convertorsytem.utils.NetworkHelper
import com.example.convertorsytem.utils.scope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CurrencyPage:Fragment(R.layout.page_currency) {

    private val binding by viewBinding(PageCurrencyBinding::bind)
    private lateinit var currencyViewModel : CurrencyViewModel
    private lateinit var currencyRvAdapter : CurrencyRvAdapter
    private lateinit var currencyList : ArrayList<Currency>


    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        currencyViewModel = ViewModelProvider(
            this@CurrencyPage , CurrencyViewModelFactory(
                CurrencyRepository(ApiClient.apiService) ,
                NetworkHelper(requireContext()) ,
                DatabaseRepository(AppDatabase.getInstance(requireContext()).cardDao())

            )
        )[CurrencyViewModel::class.java]

        loadData()

        currencyRvAdapter =
            CurrencyRvAdapter(requireContext() , object:CurrencyRvAdapter.OnCurrencyClickListener {
                override fun onItemClick(currency : Currency , position : Int) {
                    findNavController().navigate(
                        CurrencyPageDirections.actionCurrencyPageToCurrencyConvertorScreen(
                            currency ,
                            position
                        )
                    )
                }
            })
        rvCurrency.apply {
            setHasFixedSize(true)
            adapter = currencyRvAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

    }

    private fun loadData() {

        lifecycleScope.launch {
            currencyViewModel.getCurrency()
        }

        currencyViewModel.getCurrencyLiveData.observe(
            viewLifecycleOwner ,
            getCurrencyResponseObserver
        )

        currencyViewModel.errorLiveData.observe(
            viewLifecycleOwner ,
            errorObserver
        )
    }

    private val getCurrencyResponseObserver = Observer<List<CurrencyResponse>> {
        binding.swipeRefreshLayout.isRefreshing = false
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

        currencyRvAdapter.mySubmitList(currencyList)
    }

    private val errorObserver = Observer<String> {
        binding.swipeRefreshLayout.isRefreshing = false
        Snackbar.make(binding.root , "$it" , Snackbar.LENGTH_SHORT)
    }

}