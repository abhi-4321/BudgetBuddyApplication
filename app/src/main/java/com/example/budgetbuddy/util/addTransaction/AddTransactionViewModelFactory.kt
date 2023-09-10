package com.example.budgetbuddy.util.addTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.budgetbuddy.repository.TransactionRepository

class AddTransactionViewModelFactory(private val transactionRepository: TransactionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddTransactionViewModel(transactionRepository) as T
    }
}