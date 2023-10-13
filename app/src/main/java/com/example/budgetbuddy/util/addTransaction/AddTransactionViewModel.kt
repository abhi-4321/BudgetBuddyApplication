package com.example.budgetbuddy.util.addTransaction

import android.graphics.Color
import androidx.lifecycle.*
import com.airbnb.lottie.L
import com.example.budgetbuddy.repository.TransactionRepository
import com.example.budgetbuddy.ui.budget.MTransactions
import com.example.budgetbuddy.ui.transactions.Flow
import com.example.budgetbuddy.ui.transactions.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class AddTransactionViewModel(private val transactionRepository: TransactionRepository) :
    ViewModel() {

    fun insert(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.insert(transaction)
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.delete(id)
        }
    }

    fun getTransactions(): LiveData<List<Transaction>> {
        return transactionRepository.getTransactions()
    }

    fun getMonthlyTransactions(month: String): LiveData<ArrayList<MTransactions>> {
        return transactionRepository.getMonthlyTransactions(month) as LiveData<ArrayList<MTransactions>>
    }

    fun totalSpent(month: String): LiveData<Int> {
        return transactionRepository.getTotalSpent(month)
    }

    fun getTransactionsByMonth(month: String): LiveData<ArrayList<Transaction>> {
        return transactionRepository.getTransactionsByMonth(month)
    }

    fun distinctMonths(): LiveData<ArrayList<String>> {
        return transactionRepository.distinctMonths() as LiveData<ArrayList<String>>
    }

    fun getInOutFlow(): LiveData<List<Flow>> {
        return transactionRepository.getInOutFlows()
    }

    fun getTransact(id: Int): LiveData<Transaction> {
        return transactionRepository.getTransact(id)
    }
}
