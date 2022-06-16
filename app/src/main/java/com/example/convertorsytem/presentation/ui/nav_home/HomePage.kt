package com.example.convertorsytem.presentation.ui.nav_home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.convertorsytem.R
import com.example.convertorsytem.data.database.AppDatabase
import com.example.convertorsytem.data.database.entity.CardEntity
import com.example.convertorsytem.data.remote.ApiClient
import com.example.convertorsytem.data.remote.response.CurrencyResponse
import com.example.convertorsytem.databinding.BottomSheetDialogBinding
import com.example.convertorsytem.databinding.PageHomeBinding
import com.example.convertorsytem.presentation.ui.nav_currency.models.Currency
import com.example.convertorsytem.presentation.ui.nav_home.adapter.AdvertisingRvAdapter
import com.example.convertorsytem.presentation.ui.nav_home.adapter.CardRvAdapter
import com.example.convertorsytem.presentation.ui.nav_home.adapter.ServiceRvAdapter
import com.example.convertorsytem.presentation.ui.nav_home.models.Card
import com.example.convertorsytem.presentation.view_model.CardViewModel
import com.example.convertorsytem.presentation.view_model.CurrencyViewModel
import com.example.convertorsytem.presentation.view_model.CurrencyViewModelFactory
import com.example.convertorsytem.repository.CurrencyRepository
import com.example.convertorsytem.repository.DatabaseRepository
import com.example.convertorsytem.utils.NetworkHelper
import com.example.convertorsytem.utils.scope
import com.example.convertorsytem.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class HomePage:Fragment(R.layout.page_home) {

    private val binding by viewBinding(PageHomeBinding::bind)
    private lateinit var cardRvAdapter : CardRvAdapter
    private lateinit var cardList : ArrayList<Card>
    private lateinit var advertisingRvAdapter : AdvertisingRvAdapter
    private lateinit var serviceRvAdapter : ServiceRvAdapter
    private lateinit var cardViewModel : CardViewModel
    private lateinit var currencyViewModel : CurrencyViewModel
    private lateinit var currencyEur : CurrencyResponse
    private lateinit var currencyUSD : CurrencyResponse

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        setHasOptionsMenu(true)
        cardList = ArrayList()

        cardViewModel = ViewModelProvider(
            this@HomePage ,
            CurrencyViewModelFactory(
                CurrencyRepository(ApiClient.apiService) ,
                NetworkHelper(requireContext()) ,
                DatabaseRepository(AppDatabase.getInstance(requireContext()).cardDao())
            )
        )[CardViewModel::class.java]

        currencyViewModel = ViewModelProvider(
            this@HomePage ,
            CurrencyViewModelFactory(
                CurrencyRepository(ApiClient.apiService) ,
                NetworkHelper(requireContext()) ,
                DatabaseRepository(AppDatabase.getInstance(requireContext()).cardDao())
            )
        )[CurrencyViewModel::class.java]

        lifecycleScope.launch {
            currencyViewModel.getCurrency()
            cardViewModel.getAllCardInfo()
        }



        loadData()

        advertisingRvAdapter = AdvertisingRvAdapter()
        rvAdvertising.adapter = advertisingRvAdapter

        serviceRvAdapter = ServiceRvAdapter()
        rvServices.setHasFixedSize(true)
        rvServices.adapter = serviceRvAdapter
    }

    private fun loadData() {
        currencyViewModel.getCurrencyLiveData.observe(
            viewLifecycleOwner , Observer {
                it.forEach { currencyResponse ->
                    if (currencyResponse.code == "EUR") {
                        currencyEur = currencyResponse
                    }
                    if (currencyResponse.code == "USD") {
                        currencyUSD = currencyResponse
                    }
                }
            }
        )

        cardViewModel.getAllCardInfoLivaData.observe(
            viewLifecycleOwner , Observer {
                it.forEach {
                    cardList.add(
                        Card(
                            id = it.id ,
                            money = it.money.toString() ,
                            number = it.number ,
                            holderName = it.holderName ,
                            date = it.date
                        )
                    )
                }
                cardRvAdapter = CardRvAdapter(object:CardRvAdapter.OnCardClickListener {
                    override fun onClick(card : Card) {
                        val dialog = BottomSheetDialog(requireContext() , R.style.SheetDialog)
                        val bottomSheetDialogBinding =
                            BottomSheetDialogBinding.inflate(layoutInflater)
                        dialog.setContentView(bottomSheetDialogBinding.root)

                        bottomSheetDialogBinding.tabCurrency.addOnTabSelectedListener(object:
                            TabLayout.OnTabSelectedListener {
                            override fun onTabSelected(tab : TabLayout.Tab?) {
                                when (tab?.position) {
                                    0 -> {
                                        bottomSheetDialogBinding.tvExchangeMoney.text = "100000.00"
                                    }
                                    1 -> {
                                        val value =
                                            (1000000.0 / currencyEur.nbu_cell_price!!.toDouble())
                                        bottomSheetDialogBinding.tvExchangeMoney.text =
                                            "${String.format("%1.2f" , value)} EUR"

                                    }
                                    2 -> {
                                        val value =
                                            1000000.0 / currencyUSD.nbu_cell_price!!.toDouble()
                                        bottomSheetDialogBinding.tvExchangeMoney.text =
                                            "${String.format("%1.2f" , value)} USD"

                                    }
                                }
                            }

                            override fun onTabUnselected(tab : TabLayout.Tab?) {

                            }

                            override fun onTabReselected(tab : TabLayout.Tab?) {

                            }
                        })
                        dialog.show()

                    }
                })
                cardRvAdapter.mySubmitList(cardList)

                binding.rvCards.adapter = cardRvAdapter
            }
        )

    }

    override fun onCreateOptionsMenu(menu : Menu , inflater : MenuInflater) {
        super.onCreateOptionsMenu(menu , inflater)
        inflater.inflate(R.menu.home_page_menu , menu)
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            R.id.add_card -> {
                findNavController().navigate(HomePageDirections.actionHomePageToAddCardScreen())
            }
        }
        return super.onOptionsItemSelected(item)
    }

}