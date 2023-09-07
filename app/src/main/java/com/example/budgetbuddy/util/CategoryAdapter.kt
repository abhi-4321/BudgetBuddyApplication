package com.example.budgetbuddy.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R

class CategoryAdapter(private val arrayList: ArrayList<CategoryItem>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val image : ImageView = item.findViewById(R.id.image)
        val text : TextView = item.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(arrayList[position].image)
        holder.text.text = arrayList[position].text
    }
}