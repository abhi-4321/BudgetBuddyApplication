package com.example.budgetbuddy.util.addTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedTransactionViewModel : ViewModel() {

    val category : MutableLiveData<String> = MutableLiveData()

    fun setCategory(str : String) {
        category.value = str
    }

    fun getCategory() : LiveData<String> {
        return category
    }

}