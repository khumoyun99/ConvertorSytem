package com.example.convertorsytem.presentation.ui.nav_home.sceens

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
import com.example.convertorsytem.data.database.dao.CardDao
import com.example.convertorsytem.data.database.entity.CardEntity
import com.example.convertorsytem.data.remote.ApiClient
import com.example.convertorsytem.data.remote.api.BaseApi
import com.example.convertorsytem.databinding.ScreenAddCardBinding
import com.example.convertorsytem.presentation.view_model.CardViewModel
import com.example.convertorsytem.presentation.view_model.CurrencyViewModelFactory
import com.example.convertorsytem.repository.CurrencyRepository
import com.example.convertorsytem.repository.DatabaseRepository
import com.example.convertorsytem.utils.NetworkHelper
import com.example.convertorsytem.utils.scope
import com.example.convertorsytem.utils.showToast
import kotlinx.coroutines.launch

class AddCardScreen:Fragment(R.layout.screen_add_card) {

    private val binding by viewBinding(ScreenAddCardBinding::bind)
    private lateinit var cardViewModel : CardViewModel

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        cardViewModel = ViewModelProvider(
            this@AddCardScreen ,
            CurrencyViewModelFactory(
                CurrencyRepository(ApiClient.apiService) , NetworkHelper(requireContext()) ,
                DatabaseRepository(AppDatabase.getInstance(requireContext()).cardDao())
            )
        )[CardViewModel::class.java]

        binding.btnAddBtn.setOnClickListener {
            val cardNumber = etCardNumber.text.toString()
            val holderName = etCardHolderName.text.toString()
            val date = etPeriodValidity.text.toString()

            if (cardNumber.length == 16 && holderName.isNotEmpty() && date.length == 5) {
                val cardEntity = CardEntity(
                    money = 1000000.00 ,
                    number = cardNumber ,
                    holderName = holderName ,
                    date = date
                )

                cardViewModel.addCardDB(cardEntity)

            }
        }

        cardViewModel.addCardDBLiveData.observe(
            viewLifecycleOwner ,
            addDatabaseObserver
        )

        cardViewModel.errorLiveData.observe(
            viewLifecycleOwner ,
            errorObserver
        )

    }

    private val errorObserver = Observer<String> {
        showToast(it.toString())
    }

    private val addDatabaseObserver = Observer<Boolean> {
        if (it) {
            showToast("The card was successfully added")
            binding.apply {
                etCardNumber.text.clear()
                etCardHolderName.text.clear()
                etPeriodValidity.text.clear()
            }
        }

    }


}