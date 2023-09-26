package com.example.budgetbuddy.repository

import androidx.lifecycle.LiveData
import com.example.budgetbuddy.database.CreateBudgetDao
import com.example.budgetbuddy.ui.budget.Budget

class CreateBudgetRepositery(private val createBudgetDao: CreateBudgetDao) {

    suspend fun insertBudget(budget: Budget){
        createBudgetDao.insertBudget(budget)
    }

    suspend fun updateBudget( budget: Budget){
        createBudgetDao.updateBudget(budget)
    }

  suspend fun deteteBudget(budget: Budget){
       createBudgetDao.deteteBudget(budget)
   }

    fun selectBudget(): LiveData<List<Budget>> {
        return createBudgetDao.selectBudget()
    }


}