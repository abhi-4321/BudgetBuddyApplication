package com.example.budgetbuddy.repository

import androidx.lifecycle.LiveData
import com.example.budgetbuddy.database.TransactionDao
import com.example.budgetbuddy.ui.home.BarEntries
import com.example.budgetbuddy.ui.home.PieEntries

class HomeRepository(private val transactionDao: TransactionDao) {

    fun getPieEntries() : LiveData<List<PieEntries>> {
        return transactionDao.getPieEntries()
    }

    fun getMonthlyData() : LiveData<List<BarEntries>> {
        return transactionDao.getMonthlyData()
    }

    fun getWeeklyData() : LiveData<List<BarEntries>> {
        return transactionDao.getWeeklyData()
    }

}