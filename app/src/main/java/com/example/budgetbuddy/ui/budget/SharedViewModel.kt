package com.example.budgetbuddy.ui.budget

import android.os.Parcel
import android.support.v4.os.IResultReceiver._Parcel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@Suppress("PropertyName")
class SharedViewModel : ViewModel() {

    private val pair : MutableLiveData<Pair<String,String>> = MutableLiveData()

    fun addBudgets(category : String, amount : String) {
        pair.value = Pair(category,amount)
    }

    fun getBudget() : LiveData<Pair<String,String>> {
        return pair
    }

    private val _icon : MutableLiveData<Int> = MutableLiveData()
    fun setIcon(icon : Int) {
        _icon.value = icon
    }
    fun getIcon() : LiveData<Int> {
        return _icon
    }

    private val _category : MutableLiveData<String> = MutableLiveData()

    fun setCategory(category : String) {
        _category.value = category
    }

    fun getCategory() : LiveData<String> {
        return _category
    }
}