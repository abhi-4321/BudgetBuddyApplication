package com.example.budgetbuddy.util.addTransaction

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddTransactionViewModel(context: Context) : ViewModel() {

    val sharedPreferences = context.getSharedPreferences("Category",MODE_PRIVATE)

    private val category = MutableLiveData<String>()
    init {
        category.apply {
            value = sharedPreferences.getString("category","")
        }
    }
    val _category : LiveData<String> = category
}