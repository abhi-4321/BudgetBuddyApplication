package com.example.budgetbuddy.ui.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.repository.BudgetRepository

class BudgetViewModel(val budgetRepository: BudgetRepository) : ViewModel() {

    fun getBudgets() : LiveData<ArrayList<Budget>> {
        return budgetRepository.getBudgets()
    }

}