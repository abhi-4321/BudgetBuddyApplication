package com.example.budgetbuddy.util.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Viewmodel(private val repository : Repository) : ViewModel() {

    fun deleteDebit(id : Int, spent : Int, category : String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDebit(id,spent,category)
        }
    }

    fun observerData() : LiveData<Boolean> {
        return repository.observerData()
    }

    fun deleteCredit(id: Int, income : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCredit(id,income)
        }
    }
}