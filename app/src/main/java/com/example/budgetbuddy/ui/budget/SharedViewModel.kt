package com.example.budgetbuddy.ui.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val pair : MutableLiveData<Pair<String,String>> = MutableLiveData()

    fun addBudgets(category : String, amount : String) {
        pair.value = Pair(category,amount)
    }

    fun getBudget() : LiveData<Pair<String,String>> {
        return pair
    }

    val _icon : MutableLiveData<Int> = MutableLiveData()
    fun setIcon(icon : Int) {
        _icon.value = icon
    }
    fun getIcon() : LiveData<Int> {
        return _icon
    }

    val _category : MutableLiveData<String> = MutableLiveData()

    fun setCategory(category : String) {
        _category.value = category
    }

    fun getCategory() : LiveData<String> {
        return _category
    }
    val _amount : MutableLiveData<String> = MutableLiveData()

    fun getAmount() : LiveData<String> {
        return _amount
    }

    fun setAmount(amount: String) {
        _amount.value = amount
    }
}