package com.example.budgetbuddy.ui.transactions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.example.budgetbuddy.util.addTransaction.Transaction

class TransactionsAdapter(private val list: ArrayList<Transaction>) :
    RecyclerView.Adapter<TransactionsAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val category: TextView = item.findViewById(R.id.category)
        val date: TextView = item.findViewById(R.id.date)
        val time: TextView = item.findViewById(R.id.time)
        val mode: TextView = item.findViewById(R.id.mode)
        val amount: TextView = item.findViewById(R.id.amount)
        val debit: TextView = item.findViewById(R.id.debit)
        val icon: ImageView = item.findViewById(R.id.icon)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionsAdapter.ChildViewHolder {
        return ChildViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.category.text = list[position].category
        holder.date.text = list[position].date
        holder.time.text = list[position].time
        holder.mode.text = list[position].time
        holder.amount.text = list[position].amount
        holder.debit.text = list[position].debit
        val int : Int = Drawables.asRes(list[position].icon)
        holder.icon.setImageResource(int)
    }

}
