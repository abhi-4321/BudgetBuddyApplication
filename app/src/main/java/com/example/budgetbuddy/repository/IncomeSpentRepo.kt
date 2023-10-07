package com.example.budgetbuddy.repository

import androidx.lifecycle.LiveData
import com.example.budgetbuddy.database.IncomeSpentDao
import com.example.budgetbuddy.ui.budget.IncomeSpent

class IncomeSpentRepo(private val incomeSpentDao: IncomeSpentDao) {

    suspend fun insertIncome(income : Int){
        incomeSpentDao.insertIncome(income)
    }

    suspend fun insertSpent(spent : Int){
        incomeSpentDao.insertSpent(spent)
    }

    suspend fun updateSpent(spent : Int){
        incomeSpentDao.updateSpent(spent)
    }

    suspend fun updateIncome(income : Int){
        incomeSpentDao.updateIncome(income)
    }

    fun gets() : LiveData<ArrayList<IncomeSpent>> {
        return incomeSpentDao.gets() as LiveData<ArrayList<IncomeSpent>>
    }
}