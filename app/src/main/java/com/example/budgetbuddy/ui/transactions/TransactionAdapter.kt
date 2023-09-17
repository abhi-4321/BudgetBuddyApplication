package com.example.budgetbuddy.ui.transactions

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.example.budgetbuddy.util.addTransaction.Transaction

class TransactionAdapter(private val onLoadMore: ()->Unit):RecyclerView.Adapter<TransactionViewHolder>() {
    val list = ArrayList<Transaction>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
      return TransactionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout,parent,false))
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.itemModel = list[position]
        holder.update()
        if (position== list.size-1){
            onLoadMore()
        }
    }
    @SuppressLint( "NotifyDataSetChanged")
    fun reload(list : ArrayList<Transaction>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
    fun loadMore(list:ArrayList<Transaction>){
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size-list.size+1, list.size)
    }

}