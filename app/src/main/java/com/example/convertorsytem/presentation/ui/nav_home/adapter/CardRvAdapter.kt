package com.example.convertorsytem.presentation.ui.nav_home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.convertorsytem.R
import com.example.convertorsytem.databinding.ItemRvCardBinding
import com.example.convertorsytem.presentation.ui.nav_home.models.Card

class CardRvAdapter(val context : Context , val listener : OnCardClickListener):
    RecyclerView.Adapter<CardRvAdapter.MyCardVH>() {

    inner class MyCardVH(private val itemRvCardBinding : ItemRvCardBinding):
        RecyclerView.ViewHolder(itemRvCardBinding.root) {

        init {
            itemView.setOnClickListener {
                listener.onClick(differConfig.currentList[absoluteAdapterPosition])
            }

            itemRvCardBinding.imgThreeDots.setOnClickListener {
                val popupMenu : PopupMenu = PopupMenu(context , itemRvCardBinding.imgThreeDots)
                popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.itemDelete->{
                            listener.onDelete(differConfig.currentList[absoluteAdapterPosition],absoluteAdapterPosition)
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        }

        fun onBind(card : Card) {
            itemRvCardBinding.apply {
                tvMoney.text = "${card.money} UZS"
                tvCardNumber.text = card.number
                tvCardHolderName.text = card.holderName
                tvDate.text = card.date
            }

        }
    }

    fun mySubmitList(carList : ArrayList<Card>) {
        differConfig.submitList(carList.distinct().toList())
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
        fun onDelete(card : Card,pos:Int)
    }
}