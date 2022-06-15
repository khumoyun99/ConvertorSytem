package com.example.convertorsytem.presentation.ui.nav_currency.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.convertorsytem.databinding.ItemSpBinding
import com.example.convertorsytem.presentation.ui.nav_currency.models.Currency
import com.squareup.picasso.Picasso

class CurrencySpinnerAdapter:BaseAdapter() {

    private lateinit var currencyList : List<Currency>

    override fun getCount() : Int {
        return currencyList.size
    }

    override fun getItem(position : Int) : Currency {
        return currencyList[position]
    }

    override fun getItemId(position : Int) : Long {
        return position.toLong()
    }

    fun mySubmitList(currencyList : List<Currency>) {
        this.currencyList = currencyList
    }


    @SuppressLint("ViewHolder")
    override fun getView(position : Int , convertView : View? , parent : ViewGroup?) : View {
        val itemSpBinding =
            ItemSpBinding.inflate(LayoutInflater.from(parent?.context) , parent , false)
        itemSpBinding.currencyNameTv.text = currencyList[position].code
        Picasso.get().load(currencyList[position].photoUrl.toString()).into(itemSpBinding.imgSp)

        return itemSpBinding.root
    }
}