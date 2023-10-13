package com.example.budgetbuddy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Transaction
import com.example.budgetbuddy.database.BudgetDao
import com.example.budgetbuddy.database.IncomeSpentDao
import com.example.budgetbuddy.database.TransactionDao

class Repository(private val transactionDao: TransactionDao,private val budgetDao: BudgetDao,private val incomeSpentDao: IncomeSpentDao) {

    private val mutableLiveData = MutableLiveData<Boolean>()

    fun observerData() : LiveData<Boolean> {
        return mutableLiveData
    }

    @Transaction
    suspend fun deleteDebit(id : Int , spent : Int, category : String){
        budgetDao.updateSpentByTransaction(spent,category)
        incomeSpentDao.deleteSpentByTransaction(spent)
        transactionDao.delete(id)
        mutableLiveData.postValue(true)
    }

    @Transaction
    suspend fun deleteCredit(id : Int, income : Int) {
        incomeSpentDao.deleteIncomeByTransaction(income)
        transactionDao.delete(id)
    }
}