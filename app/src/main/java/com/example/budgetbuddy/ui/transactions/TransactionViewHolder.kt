package com.example.budgetbuddy.ui.transactions

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.example.budgetbuddy.util.addTransaction.Transaction
import org.w3c.dom.Text
import java.lang.ref.WeakReference

class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val view = WeakReference(itemView)
    private var textViewDate: TextView? = null
    private var category: TextView? = null
    private var date: TextView? = null
    private var time: TextView? = null
    private var mode: TextView? = null
    private var amount: TextView? = null
    private var debit: TextView? = null
    private var icon: ImageView? = null
    var itemModel: Transaction? = null

    init {
        view.get()?.let {
            category = it.findViewById(R.id.category)
            date = it.findViewById(R.id.date)
            time = it.findViewById(R.id.time)
            mode = it.findViewById(R.id.mode)
            amount = it.findViewById(R.id.amount)
            debit = it.findViewById(R.id.debit)
            icon = it.findViewById(R.id.icon)
            textViewDate = it.findViewById(R.id.textviewDate)
        }
    }

    fun update() {
        if (itemModel?.debit!! == "Credit") {
            debit?.text = "+"
            amount?.setTextColor(Color.parseColor("#118104"))
            debit?.setTextColor(Color.parseColor("#118104"))
        }
        else {
            debit?.text = "-"
            amount?.setTextColor(Color.RED)
            debit?.setTextColor(Color.RED)
        }
        category?.text = itemModel?.category
        date?.text = itemModel?.date
        time?.text = itemModel?.time
        mode?.text = itemModel?.mode
        amount?.text = itemModel?.amount

        val icon : Int? = Drawables.asRes(itemModel!!.icon)
        this.icon?.setImageResource(icon!!)

        textViewDate?.text = itemModel?.date?.substring(3)
    }
}