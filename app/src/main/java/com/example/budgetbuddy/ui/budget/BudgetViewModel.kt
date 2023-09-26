package com.example.budgetbuddy.ui.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.repository.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BudgetViewModel(val budgetRepository: BudgetRepository) : ViewModel() {

    fun getBudgets() : LiveData<ArrayList<Budget>> {
        return budgetRepository.getBudgets()
    }
    fun insert(budget : Budget) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.insert(budget)
        }
    }
}