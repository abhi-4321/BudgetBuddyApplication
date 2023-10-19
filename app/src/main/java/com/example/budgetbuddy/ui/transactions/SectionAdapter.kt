package com.example.budgetbuddy.ui.transactions

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class SectionAdapter(arrayList: ArrayList<Header>,private val onClick: TransactionsAdapter.OnClick) : RecyclerView.Adapter<SectionAdapter.ParentViewHolder>() {

    private var sortedList = ArrayList<Header>()
    init {
        if (arrayList.size >= 1)
            sortedList = ArrayList(arrayList.sortedWith(MonthComparator()))
    }

    inner class MonthComparator : Comparator<Header> {
        override fun compare(obj1: Header, obj2: Header): Int {
            val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            val date1 = dateFormat.parse(obj1.section)
            val date2 = dateFormat.parse(obj2.section)
            return date2.compareTo(date1)
        }
    }

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
        holder.section.text = sortedList[position].section
        holder.recycler.apply {
            adapter = TransactionsAdapter(sortedList[position].list,onClick)
            layoutManager=LinearLayoutManager(context)
        }
    }

    override fun getItemCount(): Int {
        return sortedList.size
    }
}