package com.example.budgetbuddy.util.addTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.L
import com.example.budgetbuddy.repository.TransactionRepository
import com.example.budgetbuddy.ui.budget.MTransactions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

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

    fun getMonthlyTransactions(month : String) : LiveData<ArrayList<MTransactions>>
    {
        return transactionRepository.getMonthlyTransactions(month) as LiveData<ArrayList<MTransactions>>
    }

    fun totalSpent(month : String) : LiveData<Int>{
        return transactionRepository.getTotalSpent(month)
    }
}