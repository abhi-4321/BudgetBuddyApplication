package com.example.budgetbuddy.ui.transactions

import android.content.Context
import android.widget.FrameLayout
import android.widget.TextView
import com.example.budgetbuddy.R

class SectionViewHolder(context: Context):FrameLayout(context) {
    private lateinit var textViewDate:TextView
    init{
        inflate(context, R.layout.viewholder_section, this)
        findView()
    }

    private fun findView(){
        textViewDate =findViewById(R.id.textviewDate)
    }
    fun setDate(dateString: String){
        textViewDate.text = dateString
    }

}