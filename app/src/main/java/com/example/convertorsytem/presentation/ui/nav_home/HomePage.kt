package com.example.convertorsytem.presentation.ui.nav_home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.convertorsytem.R
import com.example.convertorsytem.databinding.PageHomeBinding
import com.example.convertorsytem.presentation.ui.nav_home.adapter.AdvertisingRvAdapter
import com.example.convertorsytem.presentation.ui.nav_home.adapter.CardRvAdapter
import com.example.convertorsytem.presentation.ui.nav_home.adapter.ServiceRvAdapter
import com.example.convertorsytem.presentation.ui.nav_home.models.Card
import com.example.convertorsytem.utils.scope

class HomePage:Fragment(R.layout.page_home) {

    private val binding by viewBinding(PageHomeBinding::bind)
    private lateinit var cardRvAdapter : CardRvAdapter
    private lateinit var cardList : ArrayList<Card>
    private lateinit var advertisingRvAdapter : AdvertisingRvAdapter
    private lateinit var serviceRvAdapter : ServiceRvAdapter

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)
        setHasOptionsMenu(true)

        loadData()
        cardRvAdapter = CardRvAdapter()
        cardRvAdapter.mySubmitList(cardList)
        rvCards.adapter = cardRvAdapter

        advertisingRvAdapter = AdvertisingRvAdapter()
        rvAdvertising.adapter = advertisingRvAdapter

        serviceRvAdapter = ServiceRvAdapter()
        rvServices.setHasFixedSize(true)
        rvServices.adapter = serviceRvAdapter
    }

    private fun loadData() {
        cardList = ArrayList()
        for (i in 0 until 4) {
            cardList.add(
                Card(
                    id = i ,
                    money = "1 235 487 UZS" ,
                    number = "8600 1402 3275 4756" ,
                    holderName = "Boltayev Xumoyun" ,
                    date = "01/23"
                )
            )
        }
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