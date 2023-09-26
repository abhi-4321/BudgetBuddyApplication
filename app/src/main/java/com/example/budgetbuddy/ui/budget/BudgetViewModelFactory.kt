package com.example.budgetbuddy.ui.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.budgetbuddy.repository.BudgetRepository

class BudgetViewModelFactory(private val budgetRepository: BudgetRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BudgetViewModel(budgetRepository) as T
    }
}