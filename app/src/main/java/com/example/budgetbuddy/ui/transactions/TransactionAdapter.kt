package com.example.budgetbuddy.ui.transactions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R

class TransactionAdapter(private val onLoadMore: ()->Unit):RecyclerView.Adapter<TranssactionViewHolder>() {
    val list = mutableListOf<TransactionModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranssactionViewHolder {
      return TranssactionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.transcation_layout,parent,false))
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: TranssactionViewHolder, position: Int) {
        holder.itemModel = list[position]
        holder.updateview()
        if (position== list.size-1){
            onLoadMore()
        }
    }
    @SuppressLint( "NotifyDataSetChanged")
    fun reload(list: MutableList<TransactionModel>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
    fun loadMore(list:MutableList<TransactionModel>){
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size-list.size+1, list.size)
    }

}