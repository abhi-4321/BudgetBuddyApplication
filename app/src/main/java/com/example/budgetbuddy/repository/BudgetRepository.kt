package com.example.budgetbuddy.repository

import androidx.lifecycle.LiveData
import com.example.budgetbuddy.database.BudgetDao
import com.example.budgetbuddy.ui.budget.Budget

class BudgetRepository(private val budgetDao: BudgetDao) {

    suspend fun insert(budget: Budget) {
        budgetDao.insert(budget)
    }

    suspend fun update(budget: Budget) {
        budgetDao.update(budget)
    }

    suspend fun delete(budget: Budget) {
        budgetDao.delete(budget)
    }

    fun getBudgets() : LiveData<ArrayList<Budget>> {
        return budgetDao.getBudgets() as LiveData<ArrayList<Budget>>
    }

    suspend fun updateSpent(spent : Int , category : String){
        budgetDao.updateSpent(spent,category)
    }

    suspend fun updateLimit(limit : Int, category: String){
        budgetDao.updateLimit(limit,category)
    }
}