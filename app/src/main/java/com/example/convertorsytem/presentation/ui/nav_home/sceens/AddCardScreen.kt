package com.example.convertorsytem.presentation.ui.nav_home.sceens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.convertorsytem.R
import com.example.convertorsytem.databinding.ScreenAddCardBinding
import com.example.convertorsytem.utils.scope

class AddCardScreen:Fragment(R.layout.screen_add_card) {

    private val binding by viewBinding(ScreenAddCardBinding::bind)

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)


    }
}