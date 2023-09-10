package com.example.budgetbuddy.util.addTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTransactionViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {

    fun insert(transaction : Transaction)
    {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.insert(transaction)
        }
    }

    fun delete(transaction : Transaction)
    {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.delete(transaction)
        }
    }

    fun getTransactions() : LiveData<List<Transaction>>
    {
        return transactionRepository.getTransactions()
    }

}