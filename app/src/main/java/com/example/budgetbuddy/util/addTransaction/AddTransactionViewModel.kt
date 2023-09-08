package com.example.budgetbuddy.util.addTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.util.category.Category
import com.example.budgetbuddy.util.category.CategoryViewModel

class AddTransactionViewModel : ViewModel() {

    val category : MutableLiveData<String> = MutableLiveData()

    fun setCategory(str : String) {
        category.value = str
    }

    fun getCategory() : LiveData<String> {
        return category
    }



}