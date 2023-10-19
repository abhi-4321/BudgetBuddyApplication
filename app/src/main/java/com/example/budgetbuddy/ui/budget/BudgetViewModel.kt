package com.example.budgetbuddy.ui.budget

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.repository.BudgetRepository
import kotlinx.coroutines.*

class BudgetViewModel(private val budgetRepository: BudgetRepository) : ViewModel() {

    fun getBudgets(): LiveData<ArrayList<Budget>> {
        return budgetRepository.getBudgets()
    }

    fun update(budget: Budget) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.update(budget)
        }
    }
    fun insert(budget: Budget) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.insert(budget)
        }
    }

    fun updateSpent(spent : Int , category : String) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.updateSpent(spent,category)
        }
    }
}