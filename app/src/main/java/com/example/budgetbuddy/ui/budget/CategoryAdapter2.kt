package com.example.budgetbuddy.ui.budget

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.example.budgetbuddy.util.category.Category

class CategoryAdapter2(
    private val arrayList: ArrayList<Category>,
    val context: Context,
) : RecyclerView.Adapter<CategoryAdapter2.ViewHolder>(){

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val image: ImageView = item.findViewById(R.id.image)
        val text: TextView = item.findViewById(R.id.category)
        val setBudget : TextView = item.findViewById(R.id.setBudget)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(R.drawable.category)
        holder.text.text = arrayList[position].category

        holder.setBudget.setOnClickListener {

        }
    }
}