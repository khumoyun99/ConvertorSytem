package com.example.convertorsytem.presentation.ui.nav_currency.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.convertorsytem.R
import com.example.convertorsytem.databinding.ItemRvCurrencyBinding
import com.example.convertorsytem.presentation.ui.nav_currency.models.Currency
import com.squareup.picasso.Picasso

class CurrencyRvAdapter(val context : Context , val listener : OnCurrencyClickListener):
    RecyclerView.Adapter<CurrencyRvAdapter.MyCurrencyVH>() {

    inner class MyCurrencyVH(private val itemRvCurrencyBinding : ItemRvCurrencyBinding):
        RecyclerView.ViewHolder(itemRvCurrencyBinding.root) {

        init {
            itemRvCurrencyBinding.calcImg.setOnClickListener {
                listener.onItemClick(
                    differConfig.currentList[absoluteAdapterPosition] ,
                    absoluteAdapterPosition
                )
            }
        }

        fun onBind(currency : Currency) {
            itemRvCurrencyBinding.apply {
                Picasso.get().load(currency.photoUrl)
                    .error(R.drawable.ic_launcher_background)
                    .into(flagImg)

                tvCbPrice.text = "${currency.cb_price} so'm"
                currencyName.text = currency.code

                if (currency.nbu_buy_price!!.isNotEmpty()) {
                    buyPriceTv.text = currency.nbu_buy_price
                } else {
                    buyPriceTv.text = currency.cb_price
                }

                if (currency.nbu_cell_price!!.isNotEmpty()) {
                    sellPriceTv.text = currency.nbu_cell_price
                } else {
                    sellPriceTv.text = currency.cb_price
                }
            }

            itemView.startAnimation(AnimationUtils.loadAnimation(context , R.anim.rv_item_anim))

        }

    }

    fun mySubmitList(currencyList : ArrayList<Currency>) {
//        differConfig.currentList.clear()
        differConfig.submitList(currencyList)
    }

    private val diffCallBack = object:DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem : Currency , newItem : Currency) : Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem : Currency , newItem : Currency) : Boolean {
            return oldItem == newItem
        }
    }

    private val differConfig = AsyncListDiffer(this , diffCallBack)


    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : MyCurrencyVH {
        return MyCurrencyVH(
            ItemRvCurrencyBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : MyCurrencyVH , position : Int) {
        holder.onBind(differConfig.currentList[position])
    }

    override fun getItemCount() : Int {
        return differConfig.currentList.size
    }

    interface OnCurrencyClickListener {
        fun onItemClick(currency : Currency , position : Int)
    }
}