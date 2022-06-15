package com.example.convertorsytem.presentation.ui.nav_home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convertorsytem.R
import com.example.convertorsytem.databinding.ItemRvServicesBinding

class ServiceRvAdapter:RecyclerView.Adapter<ServiceRvAdapter.MyServiceVH>() {

    private val imagesList = arrayListOf(
        R.drawable.ic_credit_card ,
        R.drawable.ic_add_home ,
        R.drawable.ic_auto_pay ,
        R.drawable.ic_contact_phone
    )

    private val nameList = arrayListOf(
        "My cards" ,
        "My home" ,
        "Auto pay" ,
        "Contact phone"
    )

    inner class MyServiceVH(private val itemRvServicesBinding : ItemRvServicesBinding):
        RecyclerView.ViewHolder(itemRvServicesBinding.root) {

        fun onBind(position : Int) {
            itemRvServicesBinding.apply {
                imgServicePhoto.setImageResource(imagesList[position])
                tvServiceName.text = nameList[position]
            }

        }
    }

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : MyServiceVH {
        return MyServiceVH(
            ItemRvServicesBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : MyServiceVH , position : Int) {
        holder.onBind(position)
    }

    override fun getItemCount() : Int {
        return imagesList.size
    }
}