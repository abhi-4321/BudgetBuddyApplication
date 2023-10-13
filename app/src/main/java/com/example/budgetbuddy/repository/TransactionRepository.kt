package com.example.budgetbuddy.repository

import androidx.lifecycle.LiveData
import com.example.budgetbuddy.database.TransactionDao
import com.example.budgetbuddy.ui.budget.MTransactions
import com.example.budgetbuddy.ui.transactions.Flow
import com.example.budgetbuddy.util.addTransaction.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {

    suspend fun insert(transaction: Transaction){
        transactionDao.insert(transaction)
    }

    suspend fun delete(id : Int){
        transactionDao.delete(id)
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

    fun getTransactionsByMonth(month : String) : LiveData<ArrayList<Transaction>>{
        return transactionDao.getTransactionsByMonth(month) as LiveData<ArrayList<Transaction>>
    }

    fun distinctMonths() : LiveData<List<String>> {
        return transactionDao.distinctMonths()
    }

    fun getInOutFlows() : LiveData<List<Flow>> {
        return transactionDao.getInOutFlows()
    }

    fun getTransact(id : Int): LiveData<Transaction> {
        return transactionDao.getTransact(id)
    }
}