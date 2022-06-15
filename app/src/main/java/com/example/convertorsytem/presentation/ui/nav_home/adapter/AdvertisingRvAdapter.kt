package com.example.convertorsytem.presentation.ui.nav_home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convertorsytem.R
import com.example.convertorsytem.databinding.ItemRvAdvertisingBinding

class AdvertisingRvAdapter:RecyclerView.Adapter<AdvertisingRvAdapter.MyAdvertisingVH>() {

    private val imagesList = arrayListOf(
        R.drawable.img_click_photo ,
        R.drawable.img_uzcard_photo1 ,
        R.drawable.img_click_photo1 ,
        R.drawable.img_uzcard_photo
    )

    inner class MyAdvertisingVH(private val itemRvAdvertisingBinding : ItemRvAdvertisingBinding):
        RecyclerView.ViewHolder(itemRvAdvertisingBinding.root) {

        fun onBind(img : Int) {
            itemRvAdvertisingBinding.imgAdvertisingPhoto.setImageResource(img)
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : MyAdvertisingVH {
        return MyAdvertisingVH(
            ItemRvAdvertisingBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : MyAdvertisingVH , position : Int) {
        holder.onBind(imagesList[position])
    }

    override fun getItemCount() : Int {
        return imagesList.size
    }


}