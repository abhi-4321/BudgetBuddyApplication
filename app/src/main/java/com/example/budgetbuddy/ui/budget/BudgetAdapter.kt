package com.example.budgetbuddy.ui.budget

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.transactions.Drawables

class BudgetAdapter(
    private val arrayList: ArrayList<Budget>,
    val context: Context
    ) : RecyclerView.Adapter<BudgetAdapter.ViewHolder>(){

        inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item){
            val image: ImageView = item.findViewById(R.id.image)
            val category : TextView = item.findViewById(R.id.category)
            val limit : TextView = item.findViewById(R.id.limit)
            val spent : TextView = item.findViewById(R.id.spent)
            val remaining : TextView = item.findViewById(R.id.remaining)
            val progressBar : ProgressBar = item.findViewById(R.id.budgetProgressBar)
            val edit : ImageView = item.findViewById(R.id.edit)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.budget_layput, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return arrayList.size
        }

        @SuppressLint("ResourceType")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val icon : Int = Drawables.asRes(arrayList[position].image)

            val limit = arrayList[position].limit
            holder.progressBar.max = limit
            holder.limit.text = limit.toString()

            val spent = arrayList[position].spent
            holder.progressBar.progress=spent
            holder.spent.text = spent.toString()

            val remaining = limit - spent
            holder.remaining.text = remaining.toString()

            holder.image.setImageResource(icon)
            holder.category.text = arrayList[position].category

            holder.edit.setOnClickListener {
                val dialog = CustomDialogEdiBudget(arrayList[position].image,arrayList[position].category,context)
                dialog.show()
            }
        }


}