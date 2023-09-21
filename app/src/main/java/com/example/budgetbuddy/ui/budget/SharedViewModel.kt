package com.example.budgetbuddy.ui.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

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
}