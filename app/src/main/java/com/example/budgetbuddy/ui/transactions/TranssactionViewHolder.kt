package com.example.budgetbuddy.ui.transactions

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import java.lang.ref.WeakReference

class TranssactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val view = WeakReference(itemView)
    private var textViewTitle: TextView? = null
    private var textViewDate: TextView? = null
    var itemModel: TransactionModel? = null

    init {
        view.get()?. let {
            textViewTitle = it.findViewById(R.id.textview1)
            textViewDate = it. findViewById(R. id.textview2)
        }
    }
    fun updateview(){
        textViewTitle?.text = itemModel?.title
        textViewDate?.text = itemModel?.date
    }
}