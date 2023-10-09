package com.example.budgetbuddy.ui.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R

class SectionAdapter(private val arrayList: ArrayList<Header>) : RecyclerView.Adapter<SectionAdapter.ParentViewHolder>() {

    inner class ParentViewHolder(item : View) : RecyclerView.ViewHolder(item){
        val section : TextView = item.findViewById(R.id.header)
        val recycler : RecyclerView = item.findViewById(R.id.sectionRecycler)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SectionAdapter.ParentViewHolder {
        return ParentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.parent_recycler,parent,false))
    }

    override fun onBindViewHolder(holder: SectionAdapter.ParentViewHolder, position: Int) {
        holder.section.text = arrayList[position].section
        holder.recycler.apply {
            adapter = TransactionsAdapter(arrayList[position].list)
            layoutManager=LinearLayoutManager(context)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}