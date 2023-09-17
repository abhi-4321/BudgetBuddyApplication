package com.example.budgetbuddy.util.addTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.absoluteValue

class SharedTransactionViewModel : ViewModel() {

    val category : MutableLiveData<String> = MutableLiveData()
    val icon : MutableLiveData<Int> = MutableLiveData()

    fun setCategory(str : String) {
        category.value = str
    }

    fun getCategory() : LiveData<String> {
        return category
    }

    fun setIcon(Icon: Int) {
        icon.value = Icon
    }

    fun getIcon() : LiveData<Int>{
        return icon
    }
}