package com.example.budgetbuddy.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.budgetbuddy.ui.budget.Budget

@Dao
interface CreateBudgetDao {

    @Insert
    suspend fun insertBudget(budget: Budget)

    @Update
   suspend fun updateBudget(budget: Budget)

    @Delete
    suspend fun deteteBudget(budget: Budget)

    @Query("SELECT * FROM budget")
    fun selectBudget() : LiveData<List<Budget>>
}