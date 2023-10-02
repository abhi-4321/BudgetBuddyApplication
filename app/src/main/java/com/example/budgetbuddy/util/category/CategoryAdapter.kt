package com.example.budgetbuddy.util.category

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

class CategoryAdapter(
    private val arrayList: ArrayList<Category>,
    val context: Context,
    val clickListener: ClickListener,
    val viewModel : CategoryViewModel
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val image: ImageView = item.findViewById(R.id.image)
        val text: TextView = item.findViewById(R.id.text)
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

        if (position == arrayList.size - 1) {
            holder.image.setImageResource(R.drawable.profile)
            holder.itemView.setOnClickListener {
                val customDialogNewCategory = CustomDialogNewCategory(context,viewModel)
                customDialogNewCategory.show()
            }
        } else {
            holder.itemView.setOnClickListener {
                clickListener.onItemClick(arrayList[position].category!!)
            }
        }
    }

    interface ClickListener {
        fun onItemClick(category : String)
    }
}