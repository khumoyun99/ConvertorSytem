package com.example.convertorsytem.presentation.ui.nav_home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.convertorsytem.databinding.ItemRvCardBinding
import com.example.convertorsytem.presentation.ui.nav_home.models.Card

class CardRvAdapter(val listener : OnCardClickListener):
    RecyclerView.Adapter<CardRvAdapter.MyCardVH>() {

    inner class MyCardVH(private val itemRvCardBinding : ItemRvCardBinding):
        RecyclerView.ViewHolder(itemRvCardBinding.root) {

        init {
            itemView.setOnClickListener {
                listener.onClick(differConfig.currentList[absoluteAdapterPosition])
            }
        }

        fun onBind(card : Card) {
            itemRvCardBinding.apply {
                tvMoney.text = card.money
                tvCardNumber.text = card.number
                tvCardHolderName.text = card.holderName
                tvDate.text = card.date
            }

        }
    }

    fun mySubmitList(carList : ArrayList<Card>) {
        differConfig.submitList(carList)
    }

    private val diffCallback = object:DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem : Card , newItem : Card) : Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem : Card , newItem : Card) : Boolean {
            return oldItem == newItem
        }
    }

    private var differConfig = AsyncListDiffer(this , diffCallback)

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : MyCardVH {
        return MyCardVH(
            ItemRvCardBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : MyCardVH , position : Int) {
        holder.onBind(differConfig.currentList[position])
    }

    override fun getItemCount() : Int {
        return differConfig.currentList.size
    }

    interface OnCardClickListener {
        fun onClick(card : Card)
    }
}