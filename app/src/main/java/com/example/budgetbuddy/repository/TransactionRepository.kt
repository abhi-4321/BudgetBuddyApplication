package com.example.budgetbuddy.repository

import androidx.lifecycle.LiveData
import com.example.budgetbuddy.database.TransactionDao
import com.example.budgetbuddy.ui.budget.MTransactions
import com.example.budgetbuddy.util.addTransaction.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {

    suspend fun insert(transaction: Transaction){
        transactionDao.insert(transaction)
    }

    suspend fun delete(transaction: Transaction){
        transactionDao.delete(transaction)
    }

    fun getTransactions() : LiveData<List<Transaction>> {
        return transactionDao.getTransacts()
    }

    fun getMonthlyTransactions(month : String): LiveData<List<MTransactions>> {
        return transactionDao.getMonthlyTransacts(month)
    }

    fun getTotalSpent(month : String) : LiveData<Int> {
        return transactionDao.totalSpent(month)
    }

}